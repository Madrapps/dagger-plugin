package com.madrapps.dagger.validation.inject

import com.intellij.psi.PsiElement
import com.madrapps.dagger.isInject
import com.madrapps.dagger.validation.Problem
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.toUElement

abstract class InjectProblem : Problem {

    final override fun isError(element: PsiElement): List<Problem.Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation && annotation.isInject) {
            return isError(annotation)
        }
        return emptyList()
    }

    abstract fun isError(annotation: UAnnotation): List<Problem.Error>
}