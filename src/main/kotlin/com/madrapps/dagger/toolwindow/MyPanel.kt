package com.madrapps.dagger.toolwindow

import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import com.madrapps.dagger.DaggerView
import com.madrapps.dagger.Presenter
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
        val panel = JBPanel<SimpleToolWindowPanel>(GridLayoutManager(1, 1))

        val rootNode = DefaultMutableTreeNode("Sample root node")
        for (i in 0..4) {
            val node = DefaultMutableTreeNode("Node #" + (i + 1))
            rootNode.add(node)
        }

        treeList = SimpleTree(Presenter.treeModel)
        treeList.isRootVisible = false

        val jbScrollPane = JBScrollPane(treeList, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED)
        val gridConstraints = GridConstraints()
        gridConstraints.fill = GridConstraints.FILL_BOTH
        panel.add(jbScrollPane, gridConstraints)
        return panel
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