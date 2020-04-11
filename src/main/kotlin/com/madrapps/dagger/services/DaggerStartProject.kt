package com.madrapps.dagger.services

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.task.ProjectTaskContext
import com.intellij.task.ProjectTaskListener
import com.intellij.task.ProjectTaskResult

class DaggerStartProject : StartupActivity {

    override fun runActivity(project: Project) {
        project.log("DaggerStartProject", "Project opened")
        val connection = project.messageBus.connect()
        connection.subscribe(ProjectTaskListener.TOPIC, object : ProjectTaskListener {
            override fun started(context: ProjectTaskContext) {
                super.started(context)
                project.log("TASK S - $context")
            }

            override fun finished(context: ProjectTaskContext, executionResult: ProjectTaskResult) {
                super.finished(context, executionResult)
                project.log("TASK F - $executionResult")
                project.service.process(project)
            }
        })
    }
}