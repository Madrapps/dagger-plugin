package com.madrapps.dagger.toolwindow

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.actionSystem.PlatformDataKeys.PSI_ELEMENT
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTree
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
    sourceMethod: String?
) : DefaultMutableTreeNode(SimplerNode(project, element, name, sourceMethod))

private class SimplerNode(
    project: Project, val element: PsiElement, private val content: String,
    private val sourceMethod: String?
) : SimpleNode(project) {

    init {
        icon = AllIcons.Nodes.Class
    }

    override fun getChildren(): Array<SimpleNode> = NO_CHILDREN
    override fun getName(): String = content

    override fun createPresentation(): PresentationData {
        val data = PresentationData(content, "", icon, null)
        if (sourceMethod != null) {
            data.addText(content, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(" ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
            data.addText(sourceMethod, SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES)
        }
        return data
    }
}