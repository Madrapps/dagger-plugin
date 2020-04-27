package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.util.ui.tree.TreeUtil
import com.madrapps.dagger.core.createChildTree
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import com.madrapps.dagger.toolwindow.SimplerNode
import javax.swing.tree.DefaultMutableTreeNode

class ViewChildrenAction : AbstractViewAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.ViewChildrenAction"
    }

    override val id = ID

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        if (project != null) {
            val selectedNode = project.service.getPanel().tree.selectedNode
            e.presentation.isEnabled = e.presentation.isEnabled && selectedNode != null
        }
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            if (!isSelected(e)) {
                project.log("View Children")
                project.service.viewToggler().clearSelection()
                val tree = project.service.getPanel().tree
                val selectedNode = tree.selectedNode as? SimplerNode
                if (selectedNode != null) {
                    val treeModel = project.service.treeModel
                    val root = DefaultMutableTreeNode("")
                    treeModel.setRoot(root)
                    project.service.nodes
                        .filter { it.key == selectedNode.key && it.componentKey == selectedNode.componentKey }
                        .forEach {
                            root.add(it.createChildTree(project))
                            treeModel.reload()
                        }
                }
                TreeUtil.expandAll(tree)
            }
        }
    }
}