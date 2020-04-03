package com.madrapps.dagger.toolwindow

import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel
import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo
import com.intellij.ui.treeStructure.treetable.TreeTable
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.util.ui.ColumnInfo
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
import javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
import javax.swing.tree.DefaultMutableTreeNode

class MyPanel(toolWindow: ToolWindow) : SimpleToolWindowPanel(true, true) {

    private lateinit var treeList: TreeTable
    private lateinit var treeModel: ListTreeTableModelOnColumns

    init {
        val content =
            ContentFactory.SERVICE.getInstance().createContent(this, "", false)
        toolWindow.contentManager.addContent(content)
        setContent(getContentPanel())
    }

    private fun getContentPanel(): JPanel {
        val panel = JBPanel<SimpleToolWindowPanel>(GridLayoutManager(1, 1))

        val rootNode = DefaultMutableTreeNode("Sample root node")
        for (i in 0..4) {
            val node = DefaultMutableTreeNode("Node #" + (i + 1))
            rootNode.add(node)
        }
        treeModel = ListTreeTableModelOnColumns(rootNode, ColumnInfo.EMPTY_ARRAY)
        treeList = TreeTable(treeModel)

        val jbScrollPane = JBScrollPane(treeList, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED)
        val gridConstraints = GridConstraints()
        gridConstraints.fill = GridConstraints.FILL_BOTH
        panel.add(jbScrollPane, gridConstraints)
        return panel
    }
}