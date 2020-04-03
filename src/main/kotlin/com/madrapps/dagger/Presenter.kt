package com.madrapps.dagger

import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns
import com.intellij.util.ui.ColumnInfo
import com.sun.tools.javac.code.Symbol
import dagger.model.BindingGraph
import javax.swing.tree.DefaultMutableTreeNode

object Presenter {

    val treeModel = ListTreeTableModelOnColumns(null, ColumnInfo.EMPTY_ARRAY)

    private lateinit var daggerView: DaggerView

    fun setView(daggerView: DaggerView) {
        this.daggerView = daggerView
    }

    fun updateView(list: List<String>) {
        daggerView.updateTree(list)
    }

    fun reset() {
        treeModel.setRoot(null)
        treeModel.reload()
    }

    fun addBindings(bindingGraph: BindingGraph) {
        val rootComponentNode = bindingGraph.rootComponentNode()
        val componentNodes = bindingGraph.componentNodes()
        val keys = bindingGraph.bindings().map { it.key() }

        val componentNode = DefaultMutableTreeNode((rootComponentNode.componentPath().currentComponent() as Symbol).name)
        keys.forEach {
            val keyNode = DefaultMutableTreeNode(it)
            componentNode.add(keyNode)
        }

        var root = treeModel.root
        if (root == null) {
            root = DefaultMutableTreeNode("")
            treeModel.setRoot(root)
        }
        (root as? DefaultMutableTreeNode)?.add(componentNode)
        treeModel.reload()
    }
}

interface DaggerView {

    fun updateTree(list: List<String>)
}