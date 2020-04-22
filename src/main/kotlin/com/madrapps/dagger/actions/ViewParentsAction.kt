package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service

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
            project.log("View Parent")
            project.service.viewToggler().select(ID)
        }
    }
}