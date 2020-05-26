package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.madrapps.dagger.utils.isProvides
import com.madrapps.dagger.utils.psiIdentifier
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getContainingDeclaration
import org.jetbrains.uast.toUElement

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
            this += method.validateModuleClass(
                range, "@Provides methods can only be present within a @Module or @ProducerModule"
            )
            this += method.validateTypeParameter(range, "@Provides methods may not have type parameters")
            this += method.validatePrivateMethod(range, "@Provides methods cannot be private")
            this += method.validateAbstractMethod(range, "@Provides methods cannot be abstract")
            this += method.validateCheckedExceptionMethod(
                range, "@Provides methods may only throw unchecked exceptions. %s not allowed"
            )
            this += method.validateMultipleScope(range, "@Provides methods cannot use more than one @Scope %s")
            this += method.validateMultipleQualifier(range, "@Provides methods may not use more than one @Qualifier %s")
            this += method.validateVoidReturn(range, "@Provides methods must return a value (not void)")
            this += method.validateFrameworkTypesReturn(range, "@Provides methods must not return framework types %s")
        }
    }
}
