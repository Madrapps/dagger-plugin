package com.madrapps.dagger.services.impl

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.madrapps.dagger.services.DaggerService
import com.madrapps.dagger.services.Processor
import javax.swing.tree.DefaultTreeModel

class DaggerServiceImpl(private val project: Project) : DaggerService {

    private val processor = Processor()

    override fun process(project: Project) {
        if (!processor.isRunning()) {
            processor.process(project)
        }
    }

    override val treeModel = DefaultTreeModel(null)

    override fun reset() {
        treeModel.setRoot(null)
        treeModel.reload()
    }

    override fun log(title: String, content: String) {
        Notifications.Bus.notify(
            Notification(
                "Dagger",
                title,
                content,
                NotificationType.INFORMATION
            )
        )
    }
}