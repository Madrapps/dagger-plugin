package com.madrapps.dagger.utils

import com.intellij.psi.*
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClass
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.uast.*
import org.jetbrains.uast.kotlin.KotlinUClass
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

private const val COMPONENT = "dagger.Component"
private const val COMPONENT_BUILDER = "dagger.Component.Builder"
private const val COMPONENT_FACTORY = "dagger.Component.Factory"
private const val MODULE = "dagger.Module"
private const val INJECT = "javax.inject.Inject"
private const val SCOPE = "javax.inject.Scope"
private const val QUALIFIER = "javax.inject.Qualifier"

val UClass.isAbstract: Boolean
    get() = PsiUtil.isAbstractClass(javaPsi)

val UMethod.isAbstract: Boolean
    get() = javaPsi.hasModifierProperty("abstract")

val UAnnotation.psiIdentifier: PsiElement
    get() {
        val element = sourcePsi!!
        return element.getChildOfType<PsiJavaCodeReferenceElement>()
            ?: element.getChildOfType<KtConstructorCalleeExpression>() ?: element
    }

val UAnnotation.isComponent: Boolean
    get() = qualifiedName == COMPONENT

val UClass.isComponent: Boolean
    get() = findAnnotation(COMPONENT) != null

val UClass.isComponentFactory: Boolean
    get() = findAnnotation(COMPONENT_FACTORY) != null

val UClass.isComponentBuilder: Boolean
    get() = findAnnotation(COMPONENT_BUILDER) != null

val UAnnotation.isModule: Boolean
    get() = qualifiedName == MODULE

val UClass.isModule: Boolean
    get() = findAnnotation(MODULE) != null

val UAnnotation.isInject: Boolean
    get() = qualifiedName == INJECT

fun UAnnotation.modules(): List<UClassLiteralExpression> {
    val modules = findAttributeValue("modules")
    if (modules is UClassLiteralExpression) return listOf(modules)
    if (modules is UCallExpression) return modules.valueArguments.mapNotNull { it as? UClassLiteralExpression }
    return emptyList()
}

fun PsiClass.toUClass(): UClass? = toUElement() as? UClass

fun PsiClassType.toUClass(): UClass? = resolve()?.toUClass()

@OptIn(ExperimentalContracts::class)
fun UDeclaration.isConstructor(): Boolean {
    contract {
        returns(true) implies (this@isConstructor is UMethod)
    }
    return (this as? UMethod)?.isConstructor == true
}

fun PsiAnnotation.psiClass(): PsiClass? = nameReferenceElement?.resolve() as? PsiClass

val UMethod.psiAnnotations: List<PsiAnnotation>
    get() = this.javaPsi.annotations.toList()

val PsiClass.isScope: Boolean
    get() = hasAnnotation(SCOPE)

val PsiClass.isQualifier: Boolean
    get() = hasAnnotation(QUALIFIER)

val UClass.isKotlinObject: Boolean
    get() = (this as? KotlinUClass)?.ktClass is KtObjectDeclaration