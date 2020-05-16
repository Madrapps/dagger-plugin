package com.madrapps.dagger.validation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.madrapps.dagger.services.service
import com.madrapps.dagger.validation.component.InterfaceOrAbstract
import com.madrapps.dagger.validation.component.MultipleBuilderOrFactory
import com.madrapps.dagger.validation.component.NoModuleAnnotation
import com.madrapps.dagger.validation.inject.PrivateDeclaration
import com.madrapps.dagger.validation.inject.QualifierOnConstructor
import com.madrapps.dagger.validation.inject.ScopeOnConstructor

class ValidationAnnotator : Annotator {

    private val problems = listOf(
        InterfaceOrAbstract,
        NoModuleAnnotation,
        MultipleBuilderOrFactory,
        PrivateDeclaration,
        ScopeOnConstructor,
        QualifierOnConstructor
    )

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.project.service.settings.isValidationsEnabled) {
            problems.forEach {
                val errors = it.isError(element)
                errors.forEach { (range, message) ->
                    holder.createErrorAnnotation(range, message)
//                holder.newAnnotation(ERROR, message)
//                    .range(range)
//                    .create()
                }
            }
        }
    }
}

interface Problem {
    fun isError(element: PsiElement): List<Error>

    data class Error(val range: PsiElement, val message: String)
}
