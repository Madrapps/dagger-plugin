package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.madrapps.dagger.isAbstract
import com.madrapps.dagger.isComponent
import com.madrapps.dagger.validation.Problem
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.toUElement

object InterfaceOrAbstract : Problem {

    override fun isError(element: PsiElement): List<Error> {
        val uElement = element.toUElement()
        if (uElement is UAnnotation) {
            if (uElement.isComponent) {
                val uClass = uElement.getContainingUClass() ?: return emptyList()
                if (!(uClass.isInterface || uClass.isAbstract)) {
                    val range = element.getChildOfType<PsiJavaCodeReferenceElement>()
                        ?: element.getChildOfType<KtConstructorCalleeExpression>() ?: element
                    return listOf(Error(range, "@Component may only be applied to an interface or abstract class"))
                }
            }
        }
        return emptyList()
    }
}