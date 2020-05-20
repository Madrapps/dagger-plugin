package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.madrapps.dagger.utils.isModule
import com.madrapps.dagger.utils.isProvides
import com.madrapps.dagger.utils.psiIdentifier
import org.jetbrains.uast.*

object ProvidesProblem : Problem {

    override fun isError(element: PsiElement): List<Problem.Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation && annotation.isProvides) {
            val declaration = annotation.getContainingDeclaration() ?: return emptyList()
            val range = annotation.psiIdentifier
            return when (declaration) {
                is UMethod -> validateMethod(declaration, range)
                else -> emptyList()
            }
        }
        return emptyList()
    }

    private fun validateMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        return mutableListOf<Problem.Error>().apply {
            this += validateModuleClass(method, range)
            this += method.validateTypeParameter(range, "@provides methods may not have type parameters")
            this += method.validatePrivateMethod(range, "@provides methods cannot be private")
            this += method.validateAbstractMethod(range, "@provides methods cannot be abstract")
            this += method.validateCheckedExceptionMethod(range, "@provides methods may only throw unchecked exceptions. %s not allowed")
            this += method.validateMultipleScope(range, "@provides methods cannot use more than one @scope %s")
        }
    }

    private fun validateModuleClass(method: UMethod, range: PsiElement): List<Problem.Error> {
        val uClass = method.getContainingUClass() ?: return emptyList()
        return if (!uClass.isModule) {
            range.errors("@provides methods can only be present within a @module or @ProducerModule")
        } else emptyList()
    }
}
