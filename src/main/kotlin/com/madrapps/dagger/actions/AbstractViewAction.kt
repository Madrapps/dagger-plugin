package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.madrapps.dagger.services.service

abstract class AbstractViewAction : ToggleAction() {

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        if (project != null) {
            val treeModel = project.service.treeModel
            val selectedNode = project.service.getPanel()?.tree?.selectedNode
            e.presentation.isEnabled = treeModel.root != null || selectedNode != null
        }
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        val project = e.project
        if (project != null) {
            return e.presentation.isEnabled && project.service.viewToggler().isSelected(id)
        }
        return false
    }

    abstract val id: String
}