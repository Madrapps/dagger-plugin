package com.madrapps.dagger.validation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity.ERROR
import com.intellij.psi.PsiElement
import com.madrapps.dagger.validation.component.InterfaceOrAbstract
import com.madrapps.dagger.validation.component.MultipleBuilderOrFactory
import com.madrapps.dagger.validation.component.NoModuleAnnotation
import com.madrapps.dagger.validation.inject.PrivateDeclaration

class ValidationAnnotator : Annotator {

    private val problems = listOf(
        InterfaceOrAbstract,
        NoModuleAnnotation,
        MultipleBuilderOrFactory,
        PrivateDeclaration
    )

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        problems.forEach {
            val errors = it.isError(element)
            errors.forEach { (range, message) ->
                holder.newAnnotation(ERROR, message)
                    .range(range)
                    .create()
            }
        }
    }
}

interface Problem {
    fun isError(element: PsiElement): List<Error>

    data class Error(val range: PsiElement, val message: String)
}
