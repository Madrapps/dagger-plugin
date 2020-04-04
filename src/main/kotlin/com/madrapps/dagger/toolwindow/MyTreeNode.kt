package com.madrapps.dagger.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.ui.treeStructure.SimpleNode

class MyTreeNode(
    project: Project,
    val element: PsiElement?,
    private val content: String
) : SimpleNode(project) {

    override fun getChildren(): Array<SimpleNode> = NO_CHILDREN

    override fun getName(): String {
        return content
    }
}