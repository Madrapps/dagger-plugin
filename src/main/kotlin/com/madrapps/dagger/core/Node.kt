package com.madrapps.dagger.core

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.madrapps.dagger.toolwindow.DaggerNode

data class Node(
    val key: String,
    val content: String,
    val sourceMethod: String?,
    val element: PsiElement,
    val nodeType: NodeType,
    val componentKey: String
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Node
        if (key != other.key) return false
        if (element != other.element) return false
        if (componentKey != other.componentKey) return false
        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + element.hashCode()
        result = 31 * result + componentKey.hashCode()
        return result
    }
}

fun Node.toDaggerNode(project: Project): DaggerNode {
    return DaggerNode(project, content, element, sourceMethod, key, nodeType, componentKey)
}

fun Node.createChildTree(project: Project, roots: MutableList<Node> = mutableListOf()): DaggerNode {
    val rootNode = this.toDaggerNode(project)
    if (!roots.contains(this)) {
        this.children.forEach {
            roots += this
            val childRootNode = it.createChildTree(project, roots)
            rootNode.add(childRootNode)
            roots -= this
        }
    }
    return rootNode
}

fun Node.createParentTree(project: Project, roots: MutableList<Node> = mutableListOf()): DaggerNode {
    val rootNode = this.toDaggerNode(project)
    roots += this
    this.parents.forEach {
        if (!roots.contains(it)) {
            val parentNode = it.createParentTree(project, roots)
            rootNode.add(parentNode)
        }
    }
    roots -= this
    return rootNode
}