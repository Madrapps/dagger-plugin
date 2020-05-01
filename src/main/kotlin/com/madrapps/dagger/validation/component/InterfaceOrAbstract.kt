package com.madrapps.dagger.validation.component

import com.madrapps.dagger.isAbstract
import com.madrapps.dagger.psiIdentifier
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass

object InterfaceOrAbstract : ComponentProblem() {

    override fun isError(annotation: UAnnotation): List<Error> {
        val uClass = annotation.getContainingUClass() ?: return emptyList()
        if (!(uClass.isInterface || uClass.isAbstract)) {
            return listOf(
                Error(
                    annotation.psiIdentifier,
                    "@Component may only be applied to an interface or abstract class"
                )
            )
        }
        return emptyList()
    }
}