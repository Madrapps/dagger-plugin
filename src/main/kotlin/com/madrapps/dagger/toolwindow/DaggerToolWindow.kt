package com.madrapps.dagger.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH
import com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.util.EditSourceOnDoubleClickHandler
import com.madrapps.dagger.actions.CollapseAllAction
import com.madrapps.dagger.actions.RefreshAction
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

class MyPanel(toolWindow: ToolWindow, project: Project) : SimpleToolWindowPanel(true, true), DaggerWindowPanel {

    override val tree: DaggerTree = DaggerTree(project.service.treeModel)

    init {
        val content = ContentFactory.SERVICE.getInstance()
            .createContent(this, "", false)
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

        tree.isRootVisible = false
        tree.toggleClickCount = 3
        tree.selectionModel.selectionMode = SINGLE_TREE_SELECTION
        EditSourceOnDoubleClickHandler.install(tree) {
            tree.expandPath(tree.selectionPath)
        }

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

    private fun initToolbar(toolbar: JPanel) {
        val manager = ActionManager.getInstance()
        val refreshAction = manager.getAction(RefreshAction.ID)
        val collapseAll = manager.getAction(CollapseAllAction.ID)

        val defaultActionGroup = DefaultActionGroup().apply {
            add(refreshAction)
            addSeparator()
            add(collapseAll)
        }

        val actionToolbar = manager.createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, defaultActionGroup, true)
        actionToolbar.setTargetComponent(toolbar)
        toolbar.add(actionToolbar.component)
    }
}

interface DaggerWindowPanel {

    val tree: DaggerTree
}