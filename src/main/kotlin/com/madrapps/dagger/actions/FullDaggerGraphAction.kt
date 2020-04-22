package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.madrapps.dagger.core.createChildTree
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import javax.swing.tree.DefaultMutableTreeNode

class FullDaggerGraphAction : AbstractViewAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.FullDaggerGraphAction"
    }

    override val id = ID

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            if (!isSelected(e)) {
                project.log("View Full Dagger graph")
                project.service.viewToggler().select(ID)

                val treeModel = project.service.treeModel
                val root = DefaultMutableTreeNode("")
                treeModel.setRoot(root)
                project.service.nodes.filter { it.parents.isEmpty() }.forEach {
                    root.add(it.createChildTree(project))
                    treeModel.reload()
                }
            }
        }
    }
}