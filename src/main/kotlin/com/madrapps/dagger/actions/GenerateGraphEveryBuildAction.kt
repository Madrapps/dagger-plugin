package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.madrapps.dagger.services.service

class GenerateGraphEveryBuildAction : ToggleAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.GenerateGraphEveryBuildAction"
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        val project = e.project ?: return false
        return project.service.settings.shouldCalculateAfterEveryBuild
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            project.service.settings.shouldCalculateAfterEveryBuild = state
        }
    }
}