package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.util.ui.tree.TreeUtil
import com.madrapps.dagger.core.createParentTree
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import com.madrapps.dagger.toolwindow.SimplerNode
import javax.swing.tree.DefaultMutableTreeNode

class ViewParentsAction : AbstractViewAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.ViewParentsAction"
    }

    override val id = ID

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        if (project != null) {
            val selectedNode = project.service.getPanel()?.tree?.selectedNode
            e.presentation.isEnabled = e.presentation.isEnabled && selectedNode != null
        }
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            if (!isSelected(e)) {
                project.log("View Parent")
                project.service.viewToggler().clearSelection()
                val tree = project.service.getPanel()?.tree
                if (tree != null) {
                    val selectedNode = tree.selectedNode as? SimplerNode
                    if (selectedNode != null) {
                        val treeModel = project.service.treeModel
                        val root = DefaultMutableTreeNode("")
                        treeModel.setRoot(root)

                        val node =
                            project.service.nodes.find { it.key == selectedNode.key && it.componentKey == selectedNode.componentKey }
                        if (node != null) {
                            project.service.nodes
                                .filter { it.key == node.key && it.element == node.element }
                                .forEach {
                                    root.add(it.createParentTree(project))
                                    treeModel.reload()
                                }
                        }
                    }
                    TreeUtil.expandAll(tree)
                }
            }
        }
    }
}