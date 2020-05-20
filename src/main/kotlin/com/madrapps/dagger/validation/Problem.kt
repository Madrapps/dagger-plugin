package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.madrapps.dagger.utils.isAbstract
import org.jetbrains.kotlin.asJava.classes.isPrivateOrParameterInPrivateMethod
import org.jetbrains.uast.UMethod

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
