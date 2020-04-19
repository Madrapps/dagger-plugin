package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.madrapps.dagger.services.log

class FullDaggerGraphAction : AbstractViewAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.FullDaggerGraphAction"
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        return super.isSelected(e)
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        e.project?.log("View Full Dagger graph")
    }
}