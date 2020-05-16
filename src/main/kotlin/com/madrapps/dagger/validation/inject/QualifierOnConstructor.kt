package com.madrapps.dagger.validation.inject

import com.madrapps.dagger.*
import com.madrapps.dagger.validation.Problem
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingDeclaration

object QualifierOnConstructor : InjectProblem() {

    override fun isError(annotation: UAnnotation): List<Problem.Error> {
        val declaration = annotation.getContainingDeclaration() ?: return emptyList()
        if (declaration.isConstructor()) {
            val jr = declaration.psiAnnotations.mapNotNull {
                val psiClass = it.psiClass()
                if (psiClass?.isQualifier == true) {
                    "@${psiClass.name}"
                } else null
            }.joinToString(", ")
            if (jr.isNotBlank()) {
                return listOf(
                    Problem.Error(
                        annotation.psiIdentifier,
                        "@Qualifier annotations [$jr] are not allowed on @Inject constructors"
                    )
                )
            }
        }
        return emptyList()
    }
}