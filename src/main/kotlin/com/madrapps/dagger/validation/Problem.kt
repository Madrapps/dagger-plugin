package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.madrapps.dagger.utils.checkExceptionsThrown
import com.madrapps.dagger.utils.isAbstract
import com.madrapps.dagger.utils.presentable
import com.madrapps.dagger.utils.scopes
import org.jetbrains.kotlin.asJava.classes.isPrivateOrParameterInPrivateMethod
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getContainingUClass

interface Problem {
    fun isError(element: PsiElement): List<Error>

    data class Error(val range: PsiElement, val message: String)
}

fun PsiElement.errors(msg: String) = mutableListOf(Problem.Error(this, msg))

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
