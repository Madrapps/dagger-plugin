package com.madrapps.dagger

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.util.ClassUtil
import com.madrapps.dagger.toolwindow.MyTreeNode
import com.sun.tools.javac.code.Symbol
import dagger.model.BindingGraph
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

object Presenter {

    val treeModel = DefaultTreeModel(null)
    lateinit var project: Project

    fun reset(project: Project) {
        this.project = project
        treeModel.setRoot(null)
        treeModel.reload()
    }

    fun addBindings(bindingGraph: BindingGraph) {
        val rootComponentNode = bindingGraph.rootComponentNode()
        val componentNodes = bindingGraph.componentNodes()
        val keys = bindingGraph.bindings().map { it.key() }


        val name = (rootComponentNode.componentPath().currentComponent() as Symbol.ClassSymbol).flatname.toString()
        val psiClass = ClassUtil.findPsiClass(PsiManager.getInstance(project), name)


        val componentNode = DefaultMutableTreeNode(
            MyTreeNode(
                project,
                psiClass,
                (rootComponentNode.componentPath().currentComponent() as Symbol).name.toString()
            )
        )
        keys.forEach {
            val keyNode = DefaultMutableTreeNode(it)
            componentNode.add(keyNode)
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