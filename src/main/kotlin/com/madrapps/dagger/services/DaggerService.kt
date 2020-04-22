package com.madrapps.dagger.services

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.madrapps.dagger.core.Node
import com.madrapps.dagger.core.ViewToggler
import com.madrapps.dagger.toolwindow.DaggerWindowPanel
import org.jetbrains.uast.UElement
import javax.swing.tree.DefaultTreeModel

interface DaggerService {
    companion object {
        fun getInstance(project: Project): DaggerService {
            return ServiceManager.getService(
                project,
                DaggerService::class.java
            )
        }
    }

    val treeModel: DefaultTreeModel
    fun reset()
    fun process(project: Project)
    fun getPsiElements(): Set<UElement>
    fun addPsiElement(element: UElement?)
    fun viewToggler(): ViewToggler


    val nodes: Set<Node>
    fun addNode(node: Node)
    fun getNode(key: String): Node?

    fun log(title: String, content: String)

    fun setPanel(panel: DaggerWindowPanel)
    fun getPanel(): DaggerWindowPanel

    val settings: Storage

    class Storage {
        var isAutoScrollToSource = false
    }
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