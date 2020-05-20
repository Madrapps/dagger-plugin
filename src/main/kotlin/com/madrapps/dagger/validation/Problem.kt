package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.madrapps.dagger.validation.ProvidesProblem.errors
import org.jetbrains.uast.UMethod

interface Problem {
    fun isError(element: PsiElement): List<Error>

    data class Error(val range: PsiElement, val message: String)

    fun PsiElement.errors(msg: String): List<Error> {
        return mutableListOf(Error(this, msg))
    }
}

fun UMethod.validateTypeParameter(range: PsiElement, error: String): List<Problem.Error> {
    return if (javaPsi.typeParameters.isNotEmpty()) {
        range.errors(error)
    } else emptyList()
}