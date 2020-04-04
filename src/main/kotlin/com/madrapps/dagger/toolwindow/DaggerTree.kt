package com.madrapps.dagger.toolwindow

import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.actionSystem.PlatformDataKeys.PSI_ELEMENT
import com.intellij.ui.treeStructure.SimpleTree
import javax.swing.tree.TreeModel

class DaggerTree(treeModel: TreeModel) : SimpleTree(treeModel), DataProvider {

    override fun getData(dataId: String): Any? {
        if (dataId == PSI_ELEMENT.name) {
            return (selectedNode as? MyTreeNode)?.element
        }
        return null
    }
}