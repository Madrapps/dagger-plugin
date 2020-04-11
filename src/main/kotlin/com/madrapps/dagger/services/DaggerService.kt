package com.madrapps.dagger.services

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import dagger.model.BindingGraph
import javax.swing.tree.TreeModel

interface DaggerService {

    val treeModel: TreeModel
    fun reset()
    fun addBindings(bindingGraph: BindingGraph)
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