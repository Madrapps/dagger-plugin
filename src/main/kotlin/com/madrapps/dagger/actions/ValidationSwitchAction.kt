package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.madrapps.dagger.services.service

class ValidationSwitchAction : ToggleAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.ValidationSwitchAction"
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        val project = e.project ?: return false
        return project.service.settings.isValidationsEnabled
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project
        if (project != null) {
            project.service.settings.isValidationsEnabled = state
        }
    }
}