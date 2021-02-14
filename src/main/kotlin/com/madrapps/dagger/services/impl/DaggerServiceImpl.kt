package com.madrapps.dagger.services.impl

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.madrapps.dagger.core.Node
import com.madrapps.dagger.core.Processor
import com.madrapps.dagger.core.ViewToggler
import com.madrapps.dagger.services.DaggerService
import com.madrapps.dagger.toolwindow.DaggerWindowPanel
import org.jetbrains.uast.UElement
import javax.swing.tree.DefaultTreeModel

@State(name = "com.madrapps.dagger", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class DaggerServiceImpl(private val project: Project) : DaggerService, PersistentStateComponent<DaggerService.Storage> {

    private val notificationGroup =
        NotificationGroup("Dagger Group", NotificationDisplayType.NONE, false)

    private var panel: DaggerWindowPanel? = null
    private val elements = mutableSetOf<UElement>()

    private val processor = Processor()
    private var storage = DaggerService.Storage()

    private val _nodes = mutableSetOf<Node>()

    override fun process(project: Project) {
        if (!processor.isRunning()) {
            processor.process(project)
        }
    }

    override fun getPsiElements(): Set<UElement> {
        return elements
    }

    override val treeModel = DefaultTreeModel(null)

    private val viewToggler = ViewToggler()

    override fun setPanel(panel: DaggerWindowPanel) {
        this.panel = panel
    }

    override fun getPanel(): DaggerWindowPanel? = panel

    override fun reset() {
        _nodes.clear()
        treeModel.setRoot(null)
        treeModel.reload()
        viewToggler.reset()
    }

    override fun viewToggler(): ViewToggler {
        return viewToggler
    }

    override fun addNode(node: Node) {
        _nodes.add(node)
    }

    override fun getNode(key: String) = nodes.find { it.key == key }

    override fun addPsiElement(element: UElement?) {
        if (element != null) elements.add(element)
    }

    override val nodes: Set<Node>
        get() = _nodes

    override fun log(title: String, content: String, project: Project) {
        ApplicationManager.getApplication().invokeLater {
            val msg = "$title : $content"
            notificationGroup.createNotification(msg, NotificationType.INFORMATION).notify(project)
        }
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