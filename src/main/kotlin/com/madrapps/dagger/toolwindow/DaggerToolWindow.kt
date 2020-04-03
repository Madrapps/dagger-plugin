package com.madrapps.dagger.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.madrapps.dagger.DaggerView

class DaggerToolWindow : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        println("DaggerToolWindow - Created")
        MyPanel(toolWindow)
    }
}