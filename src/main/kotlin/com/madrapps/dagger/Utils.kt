package com.madrapps.dagger

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.impl.light.LightMethodBuilder
import com.intellij.psi.util.ClassUtil
import com.sun.tools.javac.code.Symbol
import dagger.model.BindingGraph.ComponentNode
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.idea.facet.KotlinFacet
import java.io.File
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

fun Symbol.VarSymbol.getMethod(): Symbol.MethodSymbol {
    var parent = enclosingElement
    while (parent !is Symbol.MethodSymbol) {
        parent = parent.enclosingElement
    }
    return parent
}

fun Symbol.VarSymbol.toPsiParameter(project: Project): PsiParameter? {
    val method = getMethod()
    val psiMethod = method.toPsiMethod(project)
    if (psiMethod != null) {
        val parameters = psiMethod.parameterList.parameters.filter { it.type.canonicalText == type.toString() }
        return if (parameters.size > 1) {
            // This may not always work since we work with .class and not source files, the names may be obfuscated like 'arg1' or so.
            parameters.find { it.name == simpleName.toString() }
        } else {
            parameters.firstOrNull()
        }
    }
    return null
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

fun Element.toPsiElement(project: Project): PsiElement? {
    return when (this) {
        is Symbol.VarSymbol -> toPsiParameter(project)
        is Symbol.MethodSymbol -> toPsiMethod(project)
        is Symbol.ClassSymbol -> toPsiClass(project)
        else -> null
    }
}

fun Module.getKotlinOutputDir(): File? {
    KotlinFacet.get(this)?.let {
        val path = (it.configuration.settings.compilerArguments as? K2JVMCompilerArguments)?.destination
        val file = if (path != null) File(path) else null
        if (file?.exists() == true) return file
    }
    return null
}

fun Module.getCompilerOutputFile(): File? {
    CompilerModuleExtension.getInstance(this)?.let {
        return it.compilerOutputPath?.toFileIfExists()
    }
    return null
}

fun VirtualFile.toFileIfExists(): File? {
    val file = File(this.path)
    return if (file.exists()) file else null
}