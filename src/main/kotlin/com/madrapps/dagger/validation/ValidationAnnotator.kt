package com.madrapps.dagger.validation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity.ERROR
import com.intellij.psi.PsiElement
import com.madrapps.dagger.validation.component.InterfaceOrAbstract
import com.madrapps.dagger.validation.component.NoModuleAnnotation

class ValidationAnnotator : Annotator {

    private val problems = listOf(InterfaceOrAbstract, NoModuleAnnotation)

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        problems.forEach {
            val error = it.isError(element)
            if (error != null) {
                holder.newAnnotation(ERROR, error.message)
                    .range(error.range)
                    .create()
            }
        }
    }
}

interface Problem {
    fun isError(element: PsiElement): Error?

    data class Error(val range: PsiElement, val message: String)
}
