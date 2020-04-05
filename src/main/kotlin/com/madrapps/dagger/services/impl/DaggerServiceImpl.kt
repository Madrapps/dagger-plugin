package com.madrapps.dagger.services.impl

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.madrapps.dagger.*
import com.madrapps.dagger.services.DaggerService
import com.madrapps.dagger.services.service
import com.madrapps.dagger.toolwindow.DaggerNode
import com.sun.tools.javac.code.Symbol
import com.sun.tools.javac.code.Type
import dagger.model.BindingGraph
import dagger.model.DependencyRequest
import javax.lang.model.element.Element
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class DaggerServiceImpl(private val project: Project) : DaggerService {

    override val treeModel = DefaultTreeModel(null)

    override fun reset() {
        treeModel.setRoot(null)
        treeModel.reload()
    }

    override fun addBindings(bindingGraph: BindingGraph) {

        ApplicationManager.getApplication().runReadAction {
            val rootComponentNode = bindingGraph.rootComponentNode()
            val componentNodes = bindingGraph.componentNodes()

            val componentNode =
                DaggerNode(project, rootComponentNode.name, rootComponentNode.toPsiClass(project)!!, null)

            rootComponentNode.entryPoints().forEach {
                addNodes(it, bindingGraph, componentNode, true)
            }

            var root = project.service.treeModel.root
            if (root == null) {
                root = DefaultMutableTreeNode("")
                treeModel.setRoot(root)
            }
            (root as? DefaultMutableTreeNode)?.add(componentNode)
            treeModel.reload()
        }
    }

    private fun addNodes(
        dr: DependencyRequest,
        bindingGraph: BindingGraph,
        parentNode: DaggerNode,
        isEntryPoint: Boolean
    ) {
        var currentNode = parentNode
        val key = dr.key()
        val binding = bindingGraph.bindings(key).first()
        binding.bindingElement().ifPresent { element ->
            val daggerNode = dr.createNode(element, isEntryPoint)
            if (daggerNode != null) {
                currentNode = daggerNode
                parentNode.add(currentNode)
            }
        }
        binding.dependencies().forEach {
            addNodes(it, bindingGraph, currentNode, false)
        }
    }

    private fun DependencyRequest.createNode(element: Element, isEntryPoint: Boolean): DaggerNode? {
        val name: String
        val psiElement: PsiElement
        when (element) {
            is Symbol.ClassSymbol -> {
                psiElement = element.toPsiClass(project)!!
                name = requestElement().orNull()?.name() ?: psiElement.name ?: element.simpleName.toString()
            }
            is Symbol.MethodSymbol -> {
                psiElement = element.toPsiMethod(project)!!
                name = requestElement().orNull()?.name() ?: if (psiElement.isConstructor) {
                    psiElement.name
                } else {
                    psiElement.returnType?.presentableText ?: "NULL"
                }
            }
            is Symbol.VarSymbol -> {
                psiElement = element.toPsiParameter(project)!!
                name = requestElement().orNull()?.name() ?: psiElement.type.presentableText
            }
            else -> return null
        }
        val sourceMethod = if (isEntryPoint) sourceMethod() else null
        return DaggerNode(
            project,
            name,
            psiElement,
            sourceMethod
        )
    }

    private fun DependencyRequest.sourceMethod(): String? {
        val element = requestElement().orNull()
        return if (element is Symbol.MethodSymbol) {
            "${element.simpleName}(${element.params.joinToString(",") { it.type.tsym.simpleName }})"
        } else {
            element?.toString()
        }
    }

    private fun Element.name(): String? {
        return if (this is Symbol.MethodSymbol) {
            returnType.tsym.simpleName.toString()
        } else if (this is Symbol.VarSymbol) {
            val classType = type as? Type.ClassType
            if (classType != null) {
                var name = classType.tsym.simpleName.toString()
                val params = classType.typarams_field
                if (params.isNotEmpty()) {
                    name += "<${params.joinToString(",") { it.tsym.simpleName }}>"
                }
                return name
            } else null
        } else null
    }
}