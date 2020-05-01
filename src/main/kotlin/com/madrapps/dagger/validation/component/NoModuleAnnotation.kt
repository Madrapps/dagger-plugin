package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiElement
import com.madrapps.dagger.isComponent
import com.madrapps.dagger.isModule
import com.madrapps.dagger.modules
import com.madrapps.dagger.toUClass
import com.madrapps.dagger.validation.Problem
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.toUElement

object NoModuleAnnotation : Problem {

    override fun isError(element: PsiElement): List<Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation) {
            if (annotation.isComponent) {
                return annotation.modules().mapNotNull {
                    val uClass = (it.type as? PsiClassType)?.toUClass()
                    if (uClass?.isModule == false) {
                        it.sourcePsi?.let { range ->
                            Error(range, "${uClass.javaPsi.name} is not annotated with @Module")
                        }
                    } else null
                }
            }
        }
        return emptyList()
    }
}