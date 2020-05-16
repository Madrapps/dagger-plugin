package com.madrapps.dagger.validation.component

import com.madrapps.dagger.utils.isComponentBuilder
import com.madrapps.dagger.utils.isComponentFactory
import com.madrapps.dagger.utils.psiIdentifier
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass

object MultipleBuilderOrFactory : ComponentProblem() {

    override fun isError(annotation: UAnnotation): List<Error> {
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
        return emptyList()
    }
}