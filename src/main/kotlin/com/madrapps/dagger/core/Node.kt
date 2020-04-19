package com.madrapps.dagger.core

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.madrapps.dagger.toolwindow.DaggerNode

data class Node(
    val key: String,
    val content: String,
    val sourceMethod: String?,
    val element: PsiElement,
    val nodeType: NodeType
) {

    private val _parents = mutableSetOf<Node>()
    private val _children = mutableSetOf<Node>()

    val parents: Set<Node> = _parents
    val children: Set<Node> = _children

    fun addChild(node: Node) {
        _children.add(node)
        node._parents.add(this)
    }

    fun isVisitedAlready(key: String): Boolean {
        var parent = this as Node?
        while (parent != null) {
            if (parent.key == key) {
                return true
            }
            parent = parent.parents.firstOrNull()
        }
        return false
    }
}

fun Node.toDaggerNode(project: Project): DaggerNode {
    return DaggerNode(project, content, element, sourceMethod, key, nodeType)
}

fun Node.createChildTree(project: Project): DaggerNode {
    val rootNode = this.toDaggerNode(project)
    this.children.forEach {
        val childRootNode = it.createChildTree(project)
        rootNode.add(childRootNode)
    }
    return rootNode
}