package com.madrapps.dagger.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH
import com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED
import com.intellij.uiDesigner.core.GridLayoutManager
import com.madrapps.dagger.DaggerView
import com.madrapps.dagger.Presenter
import com.madrapps.dagger.actions.RefreshAction
import java.awt.BorderLayout
import java.awt.Insets
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
import javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
import javax.swing.tree.DefaultMutableTreeNode

class MyPanel(toolWindow: ToolWindow) : SimpleToolWindowPanel(true, true), DaggerView {

    private lateinit var treeList: SimpleTree

    init {
        val content =
            ContentFactory.SERVICE.getInstance().createContent(this, "", false)
        toolWindow.contentManager.addContent(content)
        setContent(getContentPanel())
        Presenter.setView(this)
    }

    private fun getContentPanel(): JPanel {
        val panel = JBPanel<SimpleToolWindowPanel>(GridLayoutManager(2, 1, Insets(0, 0, 0, 0), 0, 0))

        treeList = SimpleTree(Presenter.treeModel)
        treeList.isRootVisible = false
        val toolbar = JPanel(BorderLayout())
        val toolbarConstraints = GridConstraints()
        toolbarConstraints.row = 0
        toolbarConstraints.fill = FILL_BOTH
        toolbarConstraints.vSizePolicy = SIZEPOLICY_FIXED

        initToolbar(toolbar)

        val jbScrollPane = JBScrollPane(treeList, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED)
        val scrollConstraints = GridConstraints()
        scrollConstraints.row = 1
        scrollConstraints.fill = FILL_BOTH

        panel.add(toolbar, toolbarConstraints)
        panel.add(jbScrollPane, scrollConstraints)

        return panel
    }

    private fun initToolbar(toolbar: JPanel) {
        val manager = ActionManager.getInstance()
        val refreshAction = manager.getAction(RefreshAction.ID)

        val defaultActionGroup = DefaultActionGroup()
        defaultActionGroup.add(refreshAction)
        defaultActionGroup.addSeparator()

        val actionToolbar = manager.createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, defaultActionGroup, true)
        actionToolbar.setTargetComponent(toolbar)
        toolbar.add(actionToolbar.component)
    }

    override fun updateTree(list: List<String>) {
        val rootNode = DefaultMutableTreeNode("Sample")
        listOf("1", "2", "3").forEach {
            rootNode.add(DefaultMutableTreeNode(it))
        }
        val treeModel = Presenter.treeModel
        treeModel.setRoot(rootNode)
        treeModel.reload(rootNode)
    }
}