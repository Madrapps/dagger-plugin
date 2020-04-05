package com.madrapps.dagger

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.psi.impl.light.LightMethodBuilder
import com.intellij.psi.util.ClassUtil
import com.sun.tools.javac.code.Symbol
import dagger.model.BindingGraph.ComponentNode
import java.util.*
import javax.lang.model.element.Element

fun <T> Optional<T>.orNull(): T? = orElse(null)

val ComponentNode.name: String
    get() {
        return componentPath().currentComponent().simpleName.toString()
    }

val ComponentNode.qualifiedName: String
    get() {
        return componentPath().currentComponent().qualifiedName.toString()
    }

fun ComponentNode.toPsiClass(project: Project): PsiClass? {
    return ClassUtil.findPsiClass(PsiManager.getInstance(project), qualifiedName)
}

fun Element.getClass(): Symbol.ClassSymbol {
    var parent = enclosingElement
    while (parent !is Symbol.ClassSymbol) {
        parent = parent.enclosingElement
    }
    return parent
}

fun Symbol.ClassSymbol.toPsiClass(project: Project): PsiClass? {
    return ClassUtil.findPsiClass(PsiManager.getInstance(project), className())
}

fun Symbol.MethodSymbol.toPsiMethod(project: Project): PsiMethod? {
    val psiManager = PsiManager.getInstance(project)
    val psiClass = this.getClass().toPsiClass(project)
    if (psiClass != null) {
        var isConstructor = false
        val methodName = if (name.toString() == "<init>") {
            isConstructor = true
            psiClass.name
        } else {
            name.toString()
        }
        val patternMethod = LightMethodBuilder(psiManager, methodName)
            .setMethodReturnType(returnType.toString())
            .setConstructor(isConstructor)
        params().forEach {
            patternMethod.addParameter(it.name.toString(), it.type.toString())
        }
        return psiClass.findMethodBySignature(patternMethod, false)
    }
    return null
}