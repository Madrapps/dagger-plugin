package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service

class FullDaggerGraphAction : AbstractViewAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.FullDaggerGraphAction"
    }

    override val id = ID

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            project.log("View Full Dagger graph")
            project.service.viewToggler().select(ID)
        }
    }
}