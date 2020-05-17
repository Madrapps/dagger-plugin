package com.madrapps.dagger.validation

import com.intellij.psi.PsiElement
import com.madrapps.dagger.utils.*
import org.jetbrains.kotlin.asJava.classes.isPrivateOrParameterInPrivateMethod
import org.jetbrains.uast.*

object InjectProblem : Problem {

    override fun isError(element: PsiElement): List<Problem.Error> {
        val annotation = element.toUElement()
        if (annotation is UAnnotation && annotation.isInject) {
            val declaration = annotation.getContainingDeclaration() ?: return emptyList()
            val range = annotation.psiIdentifier
            return when (declaration) {
                is UMethod -> validateMethod(declaration, range)
                is UVariable -> validateField(declaration, range)
                else -> emptyList()
            }
        }
        return emptyList()
    }

    private fun validateField(field: UVariable, range: PsiElement): List<Problem.Error> {
        return validatePrivateField(field, range)
    }

    private fun validateMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        return if (method.isConstructor) {
            validateConstructor(method, range)
        } else {
            validatePrivateMethod(method, range)
        }
    }

    private fun validateConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        val errors = mutableListOf<Problem.Error>()
        errors += validatePrivateConstructor(method, range)
        errors += validateScopeOnConstructor(method, range)
        errors += validateQualifierOnConstructor(method, range)
        errors += validateAbstractClass(method, range)
        return errors
    }

    private fun validateAbstractClass(method: UMethod, range: PsiElement): List<Problem.Error> {
        if (method.getContainingUClass()?.isAbstract == true) {
            return range.errors("@Inject is nonsense on the constructor of an abstract class")
        }
        return emptyList()
    }

    private fun validateScopeOnConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        val scopes = method.getPresentableAnnotatedScopes()
        if (scopes.isNotBlank()) {
            return range.errors("@Scope annotations [$scopes] are not allowed on @Inject constructors; annotate the class instead")
        }
        return emptyList()
    }

    private fun validateQualifierOnConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        val qualifiers = method.getPresentableAnnotatedQualifiers()
        if (qualifiers.isNotBlank()) {
            return range.errors("@Qualifier annotations [$qualifiers] are not allowed on @Inject constructors")
        }
        return emptyList()
    }

    private fun validatePrivateConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        if (method.isPrivateOrParameterInPrivateMethod()) {
            return range.errors("Dagger does not support injection into private constructors")
        }
        return emptyList()
    }

    private fun validatePrivateMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        if (method.isPrivateOrParameterInPrivateMethod()) {
            return range.errors("Dagger does not support injection into private methods")
        }
        return emptyList()
    }

    private fun validatePrivateField(field: UVariable, range: PsiElement): List<Problem.Error> {
        if (field.isPrivateOrParameterInPrivateMethod()) {
            return range.errors("Dagger does not support injection into private fields")
        }
        return emptyList()
    }
}

private fun PsiElement.errors(msg: String): List<Problem.Error> {
    return mutableListOf(Problem.Error(this, msg))
}

private fun UMethod.getPresentableAnnotatedScopes(): String {
    return psiAnnotations.mapNotNull {
        val psiClass = it.psiClass()
        if (psiClass?.isScope == true) "@${psiClass.name}" else null
    }.joinToString(", ")
}

private fun UMethod.getPresentableAnnotatedQualifiers(): String {
    return psiAnnotations.mapNotNull {
        val psiClass = it.psiClass()
        if (psiClass?.isQualifier == true) "@${psiClass.name}" else null
    }.joinToString(", ")
}

