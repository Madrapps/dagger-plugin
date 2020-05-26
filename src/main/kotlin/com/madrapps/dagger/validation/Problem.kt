package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.madrapps.dagger.utils.*
import org.jetbrains.kotlin.asJava.classes.isPrivateOrParameterInPrivateMethod
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getContainingUClass

interface Problem {
    fun isError(element: PsiElement): List<Error>

    data class Error(val range: PsiElement, val message: String)
}

fun PsiElement.errors(msg: String) = mutableListOf(Problem.Error(this, msg))

private val frameworkTypes = listOf("dagger.Lazy", "dagger.MembersInjector", "javax.inject.Provider")

fun UMethod.validateTypeParameter(range: PsiElement, error: String): List<Problem.Error> {
    return if (javaPsi.typeParameters.isNotEmpty()) {
        range.errors(error)
    } else emptyList()
}

fun UMethod.validatePrivateMethod(range: PsiElement, error: String): List<Problem.Error> {
    return if (isPrivateOrParameterInPrivateMethod()) {
        range.errors(error)
    } else emptyList()
}

fun UMethod.validateAbstractMethod(range: PsiElement, error: String): List<Problem.Error> {
    return if (isAbstract) {
        range.errors(error)
    } else emptyList()
}

fun UMethod.validateMustBeAbstractMethod(range: PsiElement, error: String): List<Problem.Error> {
    return if (!isAbstract) {
        range.errors(error)
    } else emptyList()
}

fun UMethod.validateCheckedExceptionMethod(range: PsiElement, error: String): List<Problem.Error> {
    val checkedExceptions = checkExceptionsThrown()
    return if (checkedExceptions.isNotEmpty()) {
        val msg = String.format(error, checkedExceptions.presentable)
        range.errors(msg)
    } else emptyList()
}

fun UMethod.validateMultipleScope(range: PsiElement, error: String): List<Problem.Error> {
    val scopes = scopes()
    return if (scopes.size > 1) {
        range.errors(String.format(error, scopes.presentable))
    } else emptyList()
}

fun UMethod.validateMultipleQualifier(range: PsiElement, error: String): List<Problem.Error> {
    val qualifiers = qualifiers()
    return if (qualifiers.size > 1) {
        range.errors(String.format(error, qualifiers.presentable))
    } else emptyList()
}

fun UMethod.validateVoidReturn(range: PsiElement, error: String): List<Problem.Error> {
    return if (returnType?.canonicalText == "void") {
        range.errors(error)
    } else emptyList()
}

fun UMethod.validateFrameworkTypesReturn(range: PsiElement, error: String): List<Problem.Error> {
    val psiClass = (returnType as? PsiClassReferenceType)?.resolve() ?: return emptyList()
    val qualifiedName = psiClass.qualifiedName
    return if (qualifiedName in frameworkTypes) {
        range.errors(String.format(error, "[${psiClass.name}]"))
    } else emptyList()
}

fun UMethod.validateModuleClass(range: PsiElement, error: String): List<Problem.Error> {
    val uClass = getContainingUClass() ?: return emptyList()
    return if (!uClass.isModule) {
        range.errors(error)
    } else emptyList()
}