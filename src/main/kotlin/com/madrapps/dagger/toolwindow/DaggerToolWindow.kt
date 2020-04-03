package com.madrapps.dagger.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo
import com.intellij.ui.treeStructure.treetable.TreeTable
import com.madrapps.dagger.DaggerView
import com.madrapps.dagger.Presenter
import javax.swing.tree.DefaultMutableTreeNode

class DaggerToolWindow : ToolWindowFactory, DaggerView {

    private lateinit var treeList: TreeTable
    private lateinit var treeModel: ListTreeTableModel

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        println("DaggerToolWindow - Created")
//        val rootNode = DefaultMutableTreeNode("Sample root node")
//        for (i in 0..4) {
//            val node = DefaultMutableTreeNode("Node #" + (i + 1))
//            rootNode.add(node)
//        }
//        treeModel = ListTreeTableModel(rootNode, arrayOf(TreeColumnInfo("1")))
//        treeList = TreeTable(treeModel)
//
//
//
//        toolWindow.contentManager.component.add(treeList)

        Presenter.setView(this)
        MyPanel(toolWindow)
    }

    override fun updateTree(list: List<String>) {
//        val rootNode = DefaultMutableTreeNode("Sample root node")
//        list.forEach {
//            rootNode.add(DefaultMutableTreeNode(it))
//        }
//        treeModel.setRoot(rootNode)
//        treeModel.reload(rootNode)
    }
}