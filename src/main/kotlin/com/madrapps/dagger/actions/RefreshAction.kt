package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.madrapps.dagger.services.Processor

class RefreshAction : AnAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.RefreshAction"
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: throw IllegalArgumentException()
        Processor().process(project)
    }
}
