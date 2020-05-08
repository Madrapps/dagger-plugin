package com.madrapps.dagger.validation.inject

import com.intellij.psi.PsiClass
import com.madrapps.dagger.psiIdentifier
import com.madrapps.dagger.validation.Problem
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getContainingDeclaration

object ScopeOnConstructor : InjectProblem() {

    override fun isError(annotation: UAnnotation): List<Problem.Error> {
        val declaration = annotation.getContainingDeclaration() ?: return emptyList()
        if (declaration.isConstructor()) {
            val annotations = declaration.getAnnotations()
            val jr = annotations.mapNotNull {
                val psiClass = it.nameReferenceElement?.resolve() as? PsiClass
                if (psiClass?.hasAnnotation("javax.inject.Scope") == true) {
                    "@${psiClass.name}"
                } else null
            }.joinToString(", ")
            if (jr.isNotBlank()) {
                return listOf(Problem.Error(
                    annotation.psiIdentifier,
                    "@Scope annotations [$jr] are not allowed on @Inject constructors; annotate the class instead"
                ))
            }
        }
        return emptyList()
    }

    private fun UDeclaration.isConstructor() =
        (this as? UMethod)?.isConstructor == true
}