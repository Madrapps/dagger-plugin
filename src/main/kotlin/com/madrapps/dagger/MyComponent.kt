package com.madrapps.dagger

import com.intellij.execution.ExecutionListener
import com.intellij.execution.ExecutionManager
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.ProjectManager

class MyComponent : ProjectComponent {

    override fun projectClosed() {
        super.projectClosed()
        val defaultProject = ProjectManager.getInstance().defaultProject
        defaultProject.messageBus.connect().subscribe(ExecutionManager.EXECUTION_TOPIC, object : ExecutionListener {
            override fun processStarted(executorId: String, env: ExecutionEnvironment, handler: ProcessHandler) {
                super.processStarted(executorId, env, handler)
                println("TTRRTT, $env Started")
            }
        })
    }

    override fun projectOpened() {
        super.projectOpened()
        println("TTRRTT, project opened")
        val defaultProject = ProjectManager.getInstance().defaultProject
        defaultProject.messageBus.connect().subscribe(ExecutionManager.EXECUTION_TOPIC, object : ExecutionListener {
            override fun processStarted(executorId: String, env: ExecutionEnvironment, handler: ProcessHandler) {
                super.processStarted(executorId, env, handler)
                println("TTRRTT, $env Started")
            }
        })
    }
}