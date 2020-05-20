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
        return mutableListOf<Problem.Error>().apply {
            this += validateFinalField(field, range)
            this += validatePrivateField(field, range)
            this += validateStaticField(field, range)
            this += validatePrivateClass(field, range)
        }
    }

    private fun validateMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        return if (method.isConstructor) {
            validateConstructor(method, range)
        } else {
            mutableListOf<Problem.Error>().apply {
                this += method.validatePrivateMethod(range, "Dagger does not support injection into private methods")
                this += validateAbstractMethod(method, range)
                this += validateStaticMethod(method, range)
                this += method.validateTypeParameter(range, "Methods with @Inject may not declare type parameters")
                this += validateCheckExceptionMethod(method, range)
                this += validatePrivateClass(method, range)
            }
        }
    }

    private fun validateConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        return mutableListOf<Problem.Error>().apply {
            this += validatePrivateConstructor(method, range)
            this += validateScopeOnConstructor(method, range)
            this += validateQualifierOnConstructor(method, range)
            this += validateAbstractClass(method, range)
            this += validateIfSingleAnnotation(method, range)
            this += validateMultipleScope(method, range)
            this += validateCheckExceptionConstructor(method, range)
            this += validateInnerClass(method, range)
            this += validatePrivateClass(method, range)
        }
    }

    private fun validatePrivateClass(declaration: UDeclaration, range: PsiElement): List<Problem.Error> {
        val uClass = declaration.getContainingUClass() ?: return emptyList()
        return if (uClass.isPrivateOrParameterInPrivateMethod()) {
            range.errors("Dagger does not support injection into private classes")
        } else emptyList()
    }

    private fun validateCheckExceptionMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        val checkedExceptions = method.checkExceptionsThrown()
        return if (checkedExceptions.isNotEmpty()) {
            range.errors(
                "Methods with @Inject may not throw checked exceptions ${checkedExceptions.presentable}. " +
                        "Please wrap your exceptions in a RuntimeException instead."
            )
        } else emptyList()
    }

    private fun validateInnerClass(method: UMethod, range: PsiElement): List<Problem.Error> {
        val uClass = method.getContainingUClass() ?: return emptyList()
        return if (uClass.isInner) {
            range.errors("@Inject constructors are invalid on inner classes. Did you mean to make the class static?")
        } else emptyList()
    }

    private fun validateCheckExceptionConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        val checkedExceptions = method.checkExceptionsThrown()
        return if (checkedExceptions.isNotEmpty()) {
            range.errors("Dagger does not support checked exceptions ${checkedExceptions.presentable} on @Inject constructors")
        } else emptyList()
    }

    private fun validateMultipleScope(method: UMethod, range: PsiElement): List<Problem.Error> {
        val uClass = method.getContainingUClass() ?: return emptyList()
        val scopes = uClass.scopes()
        return if (scopes.size > 1) {
            range.errors("A single binding may not declare more than one @Scope ${scopes.presentable}")
        } else emptyList()
    }

    private fun validateIfSingleAnnotation(method: UMethod, range: PsiElement): List<Problem.Error> {
        val uClass = method.getContainingUClass() ?: return emptyList()
        val constructorsWithInject = uClass.methods.filter { it.isConstructor && it.isInject }.count()
        return if (constructorsWithInject > 1) {
            range.errors("Types may only contain one @Inject constructor")
        } else emptyList()
    }

    private fun validateAbstractClass(method: UMethod, range: PsiElement): List<Problem.Error> {
        return if (method.getContainingUClass()?.isAbstract == true) {
            range.errors("@Inject is nonsense on the constructor of an abstract class")
        } else emptyList()
    }

    private fun validateScopeOnConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        val scopes = method.scopes()
        return if (scopes.isNotEmpty()) {
            range.errors("@Scope annotations ${scopes.presentable} are not allowed on @Inject constructors; annotate the class instead")
        } else emptyList()
    }

    private fun validateQualifierOnConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        val qualifiers = method.qualifiers()
        return if (qualifiers.isNotEmpty()) {
            range.errors("@Qualifier annotations ${qualifiers.presentable} are not allowed on @Inject constructors")
        } else emptyList()
    }

    private fun validatePrivateConstructor(method: UMethod, range: PsiElement): List<Problem.Error> {
        return if (method.isPrivateOrParameterInPrivateMethod()) {
            range.errors("Dagger does not support injection into private constructors")
        } else emptyList()
    }

    private fun validatePrivateField(field: UVariable, range: PsiElement): List<Problem.Error> {
        return if (field.isPrivateOrParameterInPrivateMethod()) {
            range.errors("Dagger does not support injection into private fields")
        } else emptyList()
    }

    private fun validateStaticField(field: UVariable, range: PsiElement): List<Problem.Error> {
        return if (field.isStatic || field.getContainingUClass()?.isKotlinObject == true) {
            range.errors("Dagger does not support injection into static fields")
        } else emptyList()
    }

    private fun validateFinalField(field: UVariable, range: PsiElement): List<Problem.Error> {
        return if (field.isFinal) {
            range.errors("@Inject fields may not be final")
        } else emptyList()
    }

    private fun validateStaticMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        return if (method.isStatic || method.getContainingUClass()?.isKotlinObject == true) {
            range.errors("Dagger does not support injection into static methods")
        } else emptyList()
    }

    private fun validateAbstractMethod(method: UMethod, range: PsiElement): List<Problem.Error> {
        return if (method.isAbstract) {
            range.errors("Methods with @Inject may not be abstract")
        } else emptyList()
    }
}
