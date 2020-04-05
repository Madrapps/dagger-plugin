package com.madrapps.dagger.services

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import dagger.model.BindingGraph
import javax.swing.tree.TreeModel

interface DaggerService {

    val treeModel: TreeModel
    fun reset()
    fun addBindings(bindingGraph: BindingGraph)

    companion object {
        fun getInstance(project: Project): DaggerService {
            return ServiceManager.getService(
                project,
                DaggerService::class.java
            )
        }
    }
}

val Project.service: DaggerService
    get() {
        return DaggerService.getInstance(this)
    }