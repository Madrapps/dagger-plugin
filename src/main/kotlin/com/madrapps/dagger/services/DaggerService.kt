package com.madrapps.dagger.services

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.madrapps.dagger.toolwindow.DaggerWindowPanel
import javax.swing.tree.DefaultTreeModel

interface DaggerService {

    val treeModel: DefaultTreeModel
    fun reset()
    fun process(project: Project)

    companion object {
        fun getInstance(project: Project): DaggerService {
            return ServiceManager.getService(
                project,
                DaggerService::class.java
            )
        }
    }

    fun log(title: String, content: String)

    fun setPanel(panel: DaggerWindowPanel)
    fun getPanel(): DaggerWindowPanel
}

val Project.service: DaggerService
    get() {
        return DaggerService.getInstance(this)
    }

fun Project.log(title: String, content: String) {
    println("$title : $content")
    if (true) {
        service.log(title, content)
    }
}

fun Project.log(content: String) {
    log("Dagger Plugin", content)
}