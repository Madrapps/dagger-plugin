package com.madrapps.dagger.validation.inject

import com.madrapps.dagger.psiIdentifier
import com.madrapps.dagger.validation.Problem
import org.jetbrains.kotlin.asJava.classes.isPrivateOrParameterInPrivateMethod
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UVariable
import org.jetbrains.uast.getContainingDeclaration

object PrivateDeclaration : InjectProblem() {

    override fun isError(annotation: UAnnotation): List<Problem.Error> {
        val declaration = annotation.getContainingDeclaration()
        if (declaration != null && declaration.isPrivateOrParameterInPrivateMethod()) {
            val error = when (declaration) {
                is UMethod -> {
                    if (declaration.isConstructor) {
                        "Dagger does not support injection into private constructors"
                    } else {
                        "Dagger does not support injection into private methods"
                    }
                }
                is UVariable -> "Dagger does not support injection into private fields"
                else -> null
            }
            if (error != null) {
                return listOf(Problem.Error(annotation.psiIdentifier, error))
            }
        }
        return emptyList()
    }
}