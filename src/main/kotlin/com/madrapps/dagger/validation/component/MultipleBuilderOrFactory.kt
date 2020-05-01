package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiElement
import com.madrapps.dagger.isComponent
import com.madrapps.dagger.isComponentBuilder
import com.madrapps.dagger.isComponentFactory
import com.madrapps.dagger.psiIdentifier
import com.madrapps.dagger.validation.Problem
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.toUElement

object MultipleBuilderOrFactory : Problem {

    override fun isError(element: PsiElement): List<Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation) {
            if (annotation.isComponent) {
                val componentClass = annotation.getContainingUClass() ?: return emptyList()
                val buildersOrFactories = componentClass.innerClasses.filter {
                    it.isComponentBuilder || it.isComponentFactory
                }
                if (buildersOrFactories.size > 1) {
                    val err = buildersOrFactories.map { it.javaPsi.name }.joinToString(", ")
                    return listOf(
                        Error(
                            annotation.psiIdentifier,
                            "@Component has more than one @Component.Builder or @Component.Factory [$err]"
                        )
                    )
                }
            }
        }
        return emptyList()
    }
}