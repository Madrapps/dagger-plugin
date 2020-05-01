package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiElement
import com.madrapps.dagger.isComponent
import com.madrapps.dagger.validation.Problem
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.toUElement

abstract class ComponentProblem : Problem {

    final override fun isError(element: PsiElement): List<Problem.Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation && annotation.isComponent) {
            return isError(annotation)
        }
        return emptyList()
    }

    abstract fun isError(annotation: UAnnotation): List<Problem.Error>
}