package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
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
            val selectedNode = project.service.getPanel().tree.selectedNode
            e.presentation.isEnabled = e.presentation.isEnabled && selectedNode != null
        }
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            if (!isSelected(e)) {
                project.log("View Parent")
                project.service.viewToggler().clearSelection()
                val selectedNode = project.service.getPanel().tree.selectedNode as? SimplerNode
                if (selectedNode != null) {
                    val treeModel = project.service.treeModel
                    val root = DefaultMutableTreeNode("")
                    treeModel.setRoot(root)
                    project.service.nodes
                        .filter { it.key == selectedNode.key && it.componentKey == selectedNode.componentKey }
                        .forEach {
                            root.add(it.createParentTree(project))
                            treeModel.reload()
                        }
                }
            }
        }

    }
}