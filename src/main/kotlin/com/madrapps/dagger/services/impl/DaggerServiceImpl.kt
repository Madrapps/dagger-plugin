package com.madrapps.dagger.services.impl

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.madrapps.dagger.core.Processor
import com.madrapps.dagger.services.DaggerService
import com.madrapps.dagger.toolwindow.DaggerWindowPanel
import org.jetbrains.uast.UElement
import javax.swing.tree.DefaultTreeModel

@State(name = "com.madrapps.dagger", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class DaggerServiceImpl(private val project: Project) : DaggerService, PersistentStateComponent<DaggerService.Storage> {

    private lateinit var panel: DaggerWindowPanel
    private val elements = mutableSetOf<UElement>()

    private val processor = Processor()
    private var storage = DaggerService.Storage()

    override fun process(project: Project) {
        if (!processor.isRunning()) {
            processor.process(project)
        }
    }

    override fun getPsiElements(): Set<UElement> {
        return elements
    }

    override val treeModel = DefaultTreeModel(null)

    override fun setPanel(panel: DaggerWindowPanel) {
        this.panel = panel
    }

    override fun getPanel(): DaggerWindowPanel = panel

    override fun reset() {
        treeModel.setRoot(null)
        treeModel.reload()
    }

    override fun addPsiElement(element: UElement?) {
        if (element != null) elements.add(element)
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

    override fun getState(): DaggerService.Storage? {
        return storage
    }

    override fun loadState(state: DaggerService.Storage) {
        storage = state
    }

    override val settings: DaggerService.Storage
        get() = state ?: DaggerService.Storage()
}