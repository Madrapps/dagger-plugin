package com.madrapps.dagger.services

import com.intellij.openapi.application.ApplicationManager
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

//            override fun finished(result: ProjectTaskManager.Result) {
//                super.finished(result)
//                if (!result.isAborted && !result.hasErrors()) {
//                    if (project.service.settings.shouldCalculateAfterEveryBuild || project.service.treeModel.root == null) {
//                        Thread {
//                            project.log("TASK F - $result")
//                            Thread.sleep(5000)
//                            ApplicationManager.getApplication().invokeLater {
//                                project.log("TASK F", "Build Successful")
//                                project.service.process(project)
//                            }
//                        }.start()
//                    }
//                }
//            }

            override fun finished(context: ProjectTaskContext, executionResult: ProjectTaskResult) {
                super.finished(context, executionResult)
                if (project.service.settings.shouldCalculateAfterEveryBuild || project.service.treeModel.root == null) {
                    Thread {
                        project.log("TASK F - $executionResult")
                        Thread.sleep(5000)
                        ApplicationManager.getApplication().invokeLater {
                            project.log("TASK F", "Build Successful")
                            project.service.process(project)
                        }
                    }.start()
                }
            }
        })
    }
}