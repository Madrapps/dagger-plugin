package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiElement
import com.madrapps.dagger.validation.Problem
import com.madrapps.dagger.validation.Problem.Error

object MultipleBuilderOrFactory : Problem {

    override fun isError(element: PsiElement): List<Error> {
        return emptyList()
    }
}