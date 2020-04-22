package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service

class ViewParentsAction : AbstractViewAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.ViewParentsAction"
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        val project = e.project
        if (project != null) {
            return super.isSelected(e) && project.service.viewToggler().state(ID).isSelected
        }
        return false
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            project.log("View Parent")
            project.service.viewToggler().select(ID)
        }
    }
}