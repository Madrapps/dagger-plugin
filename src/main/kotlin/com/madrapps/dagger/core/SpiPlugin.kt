package com.madrapps.dagger.core

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import com.madrapps.dagger.utils.*
import dagger.model.Binding
import dagger.model.BindingGraph
import dagger.model.BindingKind
import dagger.model.DependencyRequest
import dagger.spi.BindingGraphPlugin
import dagger.spi.DiagnosticReporter
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.swing.tree.DefaultMutableTreeNode

class SpiPlugin(private val project: Project) : BindingGraphPlugin {

    private var firstNodeCreated = false

    override fun visitGraph(
        bindingGraph: BindingGraph,
        diagnosticReporter: DiagnosticReporter
    ) {
        val rootComponentNode = bindingGraph.rootComponentNode()
        project.log("------------------------------------------------------------")
        project.log("RootComponent - $rootComponentNode")
        val componentNodes = bindingGraph.componentNodes()
        project.log("ComponentNodes - ${componentNodes.size}")
        project.log("------------------------------------------------------------\n")

        addBindings(bindingGraph)
        project.log("Tree constructed")
    }


    private fun addBindings(bindingGraph: BindingGraph) {
        ApplicationManager.getApplication().runReadAction {
            rootNode
            val componentKey = bindingGraph.rootComponentNode().toString()
            bindingGraph.componentNodes().forEach {
                val componentNode =
                    createNode(it.name, it.toPsiClass(project)!!, null, it.toString(), it.toNodeType(), componentKey)
                it.entryPoints().forEach {
                    addNodes(it, bindingGraph, componentNode, null, true, componentKey)
                }
                rootNode.add(componentNode.createChildTree(project))
                project.service.treeModel.reload()
            }
        }
    }

    private fun addNodes(
        dr: DependencyRequest,
        bindingGraph: BindingGraph,
        parentNode: Node,
        parentBinding: Binding?,
        isEntryPoint: Boolean,
        componentKey: String
    ) {
        val key = dr.key()
        var currentNode = parentNode
        val binding = bindingGraph.bindings(key).first()
        if (binding.kind().isMultibinding) {
            val classType = dr.key().type() as? DeclaredType
            if (classType != null) {
                val name = classType.presentableName()
                val psiElement = dr.requestElement().orNull()?.toPsiElement(project)!!
                currentNode = createNode(
                    name,
                    psiElement,
                    if (isEntryPoint) dr.sourceMethod() else null,
                    key.toString(),
                    binding.kind().toNodeType(),
                    componentKey
                )
                parentNode.addChild(currentNode)
            }
        } else {
            binding.bindingElement().ifPresent { element ->
                val tempNode = dr.createNode(
                    element,
                    isEntryPoint,
                    parentBinding,
                    key.toString(),
                    binding.kind().toNodeType(),
                    componentKey
                )
                if (tempNode != null) {
                    currentNode = tempNode
                    parentNode.addChild(currentNode)
                }
            }
        }
        if (!parentNode.isVisitedAlready(key.toString())) { // to avoid circular dependencies causing stackOverFlow
            binding.dependencies().filterNot { it.key().toString().startsWith("dagger.android.") }.forEach {
                addNodes(it, bindingGraph, currentNode, binding, false, componentKey)
            }
        }
    }

    private fun DependencyRequest.createNode(
        element: Element,
        isEntryPoint: Boolean,
        parentBinding: Binding?,
        key: String,
        nodeType: NodeType,
        componentKey: String
    ): Node? {
        val name: String
        val psiElement: PsiElement
        when (element) {
            is TypeElement -> {
                psiElement = element.toPsiClass(project)!!
                name = requestElement().orNull()?.name() ?: psiElement.name ?: element.simpleName.toString()
            }
            is VariableElement -> {
                psiElement = element.toPsiParameter(project)!!
                name = requestElement().orNull()?.name() ?: psiElement.type.presentableText
            }
            is ExecutableElement -> {
                psiElement = element.toPsiMethod(project)!!
                var temp = requestElement().orNull()?.name() ?: if (psiElement.isConstructor) {
                    psiElement.name
                } else {
                    psiElement.returnType?.presentableText ?: "NULL"
                }
                if (parentBinding?.kind() == BindingKind.MULTIBOUND_MAP) {
                    val find = element.annotationMirrors.find {
                        it.isStandardKey || (it.annotationType as DeclaredType).asElement().annotationMirrors.find { it.isMapKey } != null
                    }
                    val snd = find?.elementValues?.values?.first()
                    val sndText = if (snd != null) {
                        val value = snd.value
                        when (value) {
                            is VariableElement -> "${(value.asType() as DeclaredType).asElement().simpleName}.${value.simpleName}"
                            is DeclaredType -> value.asElement().simpleName.toString()
                            else -> value.toString()
                        }
                    } else "Object"
                    temp += " [$sndText]"
                }
                val snd = element.annotationMirrors.find {
                    (it.annotationType as DeclaredType).asElement().annotationMirrors.find { it.isQualifier } != null
                }
                if (snd != null) {
                    temp += "[${snd.annotationType.asElement().simpleName}]"
                }
                name = temp
            }
            else -> return null
        }
        val sourceMethod = if (isEntryPoint) sourceMethod() else null
        return createNode(
            name,
            psiElement,
            sourceMethod,
            key,
            nodeType,
            componentKey
        )
    }

    private val rootNode: DefaultMutableTreeNode
        get() {
            var root = project.service.treeModel.root
            if (!firstNodeCreated) {
                project.service.reset()
                firstNodeCreated = true
                root = DefaultMutableTreeNode("")
                project.service.treeModel.setRoot(root)
            }
            return root as DefaultMutableTreeNode
        }

    private fun createNode(
        name: String,
        element: PsiElement,
        sourceMethod: String?,
        key: String,
        nodeType: NodeType,
        componentKey: String
    ): Node {
        val node = Node(key, name, sourceMethod, element, nodeType, componentKey)
        val oldNode = project.service.nodes.find { it == node }
        return if (oldNode != null) {
            oldNode
        } else {
            project.service.addNode(node)
            node
        }
    }
}