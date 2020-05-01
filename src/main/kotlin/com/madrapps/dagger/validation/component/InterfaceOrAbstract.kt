package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiElement
import com.madrapps.dagger.isAbstract
import com.madrapps.dagger.isComponent
import com.madrapps.dagger.psiIdentifier
import com.madrapps.dagger.validation.Problem
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.toUElement

object InterfaceOrAbstract : Problem {

    override fun isError(element: PsiElement): List<Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation) {
            if (annotation.isComponent) {
                val uClass = annotation.getContainingUClass() ?: return emptyList()
                if (!(uClass.isInterface || uClass.isAbstract)) {
                    return listOf(
                        Error(
                            annotation.psiIdentifier,
                            "@Component may only be applied to an interface or abstract class"
                        )
                    )
                }
            }
        }
        return emptyList()
    }
}