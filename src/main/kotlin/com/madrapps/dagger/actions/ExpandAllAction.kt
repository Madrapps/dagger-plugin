package com.madrapps.dagger.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.util.ui.tree.TreeUtil
import com.madrapps.dagger.services.service

class ExpandAllAction : AnAction() {

    companion object {
        const val ID = "com.madrapps.dagger.actions.ExpandAllAction"
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        if (project != null) {
            val treeModel = project.service.treeModel
            e.presentation.isEnabled = treeModel.root != null
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project != null) {
            val panel = project.service.getPanel()
            if (panel != null) {
                val tree = panel.tree
                TreeUtil.expandAll(tree)
            }
        }
    }
}