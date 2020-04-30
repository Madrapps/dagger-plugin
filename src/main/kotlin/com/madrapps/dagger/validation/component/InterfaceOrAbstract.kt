package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.intellij.psi.util.PsiUtil
import com.madrapps.dagger.isAbstract
import com.madrapps.dagger.validation.Problem
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.toUElement

object InterfaceOrAbstract : Problem {

    override fun isError(element: PsiElement): Error? {
        val uElement = element.toUElement()
        if (uElement is UAnnotation) {
            if (uElement.qualifiedName == "dagger.Component") {
                val uClass = uElement.getContainingUClass() ?: return null
                if (!(uClass.isInterface || uClass.isAbstract)) {
                    val range = element.getChildOfType<PsiJavaCodeReferenceElement>()
                        ?: element.getChildOfType<KtConstructorCalleeExpression>() ?: element
                    return Error(range, "@Component may only be applied to an interface or abstract class")
                }
            }
        }
        return null
    }
}