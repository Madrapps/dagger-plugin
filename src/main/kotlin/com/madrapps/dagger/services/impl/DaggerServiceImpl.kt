package com.madrapps.dagger.services.impl

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.madrapps.dagger.*
import com.madrapps.dagger.services.DaggerService
import com.madrapps.dagger.services.service
import com.madrapps.dagger.toolwindow.DaggerNode
import com.sun.tools.javac.code.Attribute
import com.sun.tools.javac.code.Symbol
import com.sun.tools.javac.code.Type
import dagger.model.Binding
import dagger.model.BindingGraph
import dagger.model.BindingKind
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
                DaggerNode(project, rootComponentNode.name, rootComponentNode.toPsiClass(project)!!, null, "entry")

            rootComponentNode.entryPoints().forEach {
                addNodes(it, bindingGraph, componentNode, null, true)
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
        parentBinding: Binding?,
        isEntryPoint: Boolean
    ) {
        var currentNode = parentNode
        val key = dr.key()
        val binding = bindingGraph.bindings(key).first()
        if (binding.kind() == BindingKind.MULTIBOUND_MAP) {
            val classType = dr.key().type() as? Type.ClassType
            if (classType != null) {
                val name = classType.presentableName()
                val psiElement = (dr.requestElement().orNull() as Symbol.VarSymbol).toPsiParameter(project)!!
                currentNode = DaggerNode(project, name, psiElement, null, "${dr.kind()} / ${binding.kind()}")
                parentNode.add(currentNode)
            }
        } else {
            binding.bindingElement().ifPresent { element ->
                val daggerNode = dr.createNode(element, isEntryPoint, parentBinding, "${dr.kind()} / ${binding.kind()}")
                if (daggerNode != null) {
                    currentNode = daggerNode
                    parentNode.add(currentNode)
                }
            }
        }
        binding.dependencies().forEach {
            addNodes(it, bindingGraph, currentNode, binding, false)
        }
    }

    private fun DependencyRequest.createNode(
        element: Element,
        isEntryPoint: Boolean,
        parentBinding: Binding?,
        type: String
    ): DaggerNode? {
        val name: String
        val psiElement: PsiElement
        when (element) {
            is Symbol.ClassSymbol -> {
                psiElement = element.toPsiClass(project)!!
                name = requestElement().orNull()?.name() ?: psiElement.name ?: element.simpleName.toString()
            }
            is Symbol.MethodSymbol -> {
                psiElement = element.toPsiMethod(project)!!
                var temp = requestElement().orNull()?.name() ?: if (psiElement.isConstructor) {
                    psiElement.name
                } else {
                    psiElement.returnType?.presentableText ?: "NULL"
                }
                if (parentBinding?.kind() == BindingKind.MULTIBOUND_MAP) {
                    temp += " [${(element.annotationMirrors.find {
                        (it.annotationType as Type.ClassType).tsym.qualifiedName.toString() == "dagger.multibindings.StringKey"
                    } as Attribute.Compound).values.first().snd}]"
                }
                name = temp
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
            sourceMethod,
            type
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
            (type as? Type.ClassType)?.presentableName()
        } else null
    }

    private fun Type.ClassType.presentableName(): String {
        var name = tsym.simpleName.toString()
        val params = typarams_field
        if (params.isNotEmpty()) {
            name += "<${params.joinToString(",") { it.tsym.simpleName }}>"
        }
        return name
    }
}