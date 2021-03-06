package com.madrapps.dagger.toolwindow

import com.intellij.ide.projectView.PresentationData
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.actionSystem.PlatformDataKeys.PSI_ELEMENT
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTree
import com.madrapps.dagger.core.NodeType
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import org.jetbrains.uast.toUElement
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeModel

class DaggerTree(treeModel: TreeModel) : SimpleTree(treeModel), DataProvider {

    override fun getData(dataId: String): Any? {
        if (dataId == PSI_ELEMENT.name) {
            return (selectedNode as? SimplerNode)?.element
        }
        return null
    }
}

// TODO Should we make "PsiElement" as nullable?
class DaggerNode(
    project: Project,
    name: String,
    element: PsiElement,
    sourceMethod: String?,
    key: String,
    nodeType: NodeType,
    componentKey: String
) : DefaultMutableTreeNode(SimplerNode(project, element, name, sourceMethod, key, nodeType, componentKey)) {

    fun isVisitedAlready(key: String): Boolean {
        var parent = this as DaggerNode?
        while (parent != null) {
            val simplerNode = parent.userObject as SimplerNode
            if (simplerNode.key == key) {
                return true
            }
            parent = parent.parent as? DaggerNode
        }
        return false
    }
}

class SimplerNode(
    project: Project,
    val element: PsiElement,
    private val content: String,
    private val sourceMethod: String?,
    val key: String,
    private val nodeType: NodeType,
    val componentKey: String
) : SimpleNode(project) {

    init {
        project.service.addPsiElement(element.toUElement())
    }

    override fun getChildren(): Array<SimpleNode> = NO_CHILDREN
    override fun getName(): String = content

    override fun createPresentation(): PresentationData {
        val data = PresentationData(content, "", nodeType.icon, null)
        if (sourceMethod != null) {
            data.addText(content, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(sourceMethod, SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES)
        }
        return data
    }
}