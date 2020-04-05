package com.madrapps.dagger

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.util.ClassUtil
import com.madrapps.dagger.toolwindow.DaggerNode
import com.sun.tools.javac.code.Symbol
import dagger.model.BindingGraph
import dagger.model.DependencyRequest
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

object Presenter {

    val treeModel = DefaultTreeModel(null)
    private lateinit var project: Project

    fun reset(project: Project) {
        this.project = project
        treeModel.setRoot(null)
        treeModel.reload()
    }

    fun addBindings(bindingGraph: BindingGraph) {

        ApplicationManager.getApplication().runReadAction {
            val rootComponentNode = bindingGraph.rootComponentNode()
            val componentNodes = bindingGraph.componentNodes()
            val keys = bindingGraph.bindings().map { it.key() }

            val psiClass = ClassUtil.findPsiClass(PsiManager.getInstance(project), rootComponentNode.qualifiedName)

            val componentNode = DaggerNode(project, rootComponentNode.name, psiClass!!, null)

            rootComponentNode.entryPoints().forEach {
                addNodes(it, bindingGraph, componentNode)
            }

            var root = treeModel.root
            if (root == null) {
                root = DefaultMutableTreeNode("")
                treeModel.setRoot(root)
            }
            (root as? DefaultMutableTreeNode)?.add(componentNode)
            treeModel.reload()
        }
    }

    private fun addNodes(dr: DependencyRequest, bindingGraph: BindingGraph, parentNode: DaggerNode) {
        var currentNode = parentNode
        val key = dr.key()
        val binding = bindingGraph.bindings(key).first()
        binding.bindingElement().ifPresent { element ->
            if (element is Symbol.MethodSymbol) {
                currentNode = addNode(dr, element)
                parentNode.add(currentNode)
            }
        }
        binding.dependencies().forEach {
            addNodes(it, bindingGraph, currentNode)
        }
    }

    private fun addNode(
        dr: DependencyRequest,
        element: Symbol.MethodSymbol
    ): DaggerNode {
        val psiMethod = element.toPsiMethod(project)!!
        val name = if (psiMethod.isConstructor) {
            psiMethod.name
        } else {
            psiMethod.returnType?.presentableText ?: "NULL"
        }
        return DaggerNode(
            project,
            name,
            psiMethod,
            dr.requestElement().orNull()?.toString()
        )
    }
}