package com.madrapps.dagger.validation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.toUElement

class ComponentAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val uElement = element.toUElement()
        if (uElement is UAnnotation) {
            if (uElement.qualifiedName == "dagger.Component") {
                val uClass = uElement.getContainingUClass() ?: return
                if (!(uClass.isInterface || PsiUtil.isAbstractClass(uClass.javaPsi))) {
                    val ele = element.getChildOfType<PsiJavaCodeReferenceElement>() ?: element.getChildOfType<KtConstructorCalleeExpression>() ?: element
                    holder.newAnnotation(HighlightSeverity.ERROR, "@Component may only be applied to an interface or abstract class")
                        .range(ele)
                        .create()
                }
            }
        }
    }
}