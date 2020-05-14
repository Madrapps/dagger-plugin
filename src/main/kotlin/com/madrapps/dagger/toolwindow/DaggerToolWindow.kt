package com.madrapps.dagger.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ex.ToolWindowEx
import com.intellij.ui.AutoScrollToSourceHandler
import com.intellij.ui.PopupHandler
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH
import com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.util.EditSourceOnDoubleClickHandler
import com.madrapps.dagger.actions.*
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import java.awt.BorderLayout
import java.awt.Insets
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
import javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
import javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION

class DaggerToolWindow : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        project.log("DaggerToolWindow - Created")
        MyPanel(toolWindow, project)
    }
}

class MyPanel(private val toolWindow: ToolWindow, project: Project) : SimpleToolWindowPanel(true, true),
    DaggerWindowPanel {

    override val tree: DaggerTree = DaggerTree(project.service.treeModel)
    private val autoScrollHandler: AutoScrollToSourceHandler

    init {
        autoScrollHandler = object : AutoScrollToSourceHandler() {

            override fun isAutoScrollMode() = project.service.settings.isAutoScrollToSource

            override fun setAutoScrollMode(state: Boolean) {
                project.service.settings.isAutoScrollToSource = state
            }
        }

        val content = ContentFactory.SERVICE.getInstance().createContent(this, "", false)
        toolWindow.contentManager.addContent(content)
        setContent(getContentPanel())
        project.service.setPanel(this)
    }

    private fun getContentPanel(): JPanel {
        val panel = JBPanel<SimpleToolWindowPanel>(
            GridLayoutManager(
                2,
                1,
                Insets(0, 0, 0, 0),
                0,
                0
            )
        )

        val toolbar = JPanel(BorderLayout())
        initToolbar(toolbar)
        initSettingsOptions()

        tree.isRootVisible = false
        tree.toggleClickCount = 3
        tree.selectionModel.selectionMode = SINGLE_TREE_SELECTION
        EditSourceOnDoubleClickHandler.install(tree) {
            tree.expandPath(tree.selectionPath)
        }
        autoScrollHandler.install(tree)

        val jbScrollPane = JBScrollPane(tree, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED)

        panel.add(toolbar, GridConstraints().apply {
            row = 0
            fill = FILL_BOTH
            vSizePolicy = SIZEPOLICY_FIXED
        })
        panel.add(jbScrollPane, GridConstraints().apply {
            row = 1
            fill = FILL_BOTH
        })

        return panel
    }

    private fun initSettingsOptions() {
        val manager = ActionManager.getInstance()
        val validationSwitchAction = manager.getAction(ValidationSwitchAction.ID)

        val toolbarActionGroup = DefaultActionGroup().apply {
            add(validationSwitchAction)
        }
        (toolWindow as? ToolWindowEx)?.setAdditionalGearActions(toolbarActionGroup)
    }

    private fun initToolbar(toolbar: JPanel) {
        val manager = ActionManager.getInstance()
        val refreshAction = manager.getAction(RefreshAction.ID)
        val fullDaggerGraphAction = manager.getAction(FullDaggerGraphAction.ID)
        val viewParentsAction = manager.getAction(ViewParentsAction.ID)
        val viewChildrenAction = manager.getAction(ViewChildrenAction.ID)
        val autoScroll = autoScrollHandler.createToggleAction()
        val expandAll = manager.getAction(ExpandAllAction.ID)
        val collapseAll = manager.getAction(CollapseAllAction.ID)
        val generateGraphEveryBuild = manager.getAction(GenerateGraphEveryBuildAction.ID)

        val toolbarActionGroup = DefaultActionGroup().apply {
            add(refreshAction)
            addSeparator()
            add(fullDaggerGraphAction)
            add(viewParentsAction)
            add(viewChildrenAction)
            addSeparator()
            add(expandAll)
            add(collapseAll)
            addSeparator()
            add(autoScroll)
            add(generateGraphEveryBuild)
        }

        val actionToolbar = manager.createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, toolbarActionGroup, true)
        actionToolbar.setTargetComponent(toolbar)
        toolbar.add(actionToolbar.component)

        // Show actions when selected issue is right clicked
        PopupHandler.installPopupHandler(
            tree,
            DefaultActionGroup().apply {
                add(viewParentsAction)
                add(viewChildrenAction)
            },
            ActionPlaces.POPUP,
            ActionManager.getInstance()
        )
    }
}

interface DaggerWindowPanel {

    val tree: DaggerTree
}