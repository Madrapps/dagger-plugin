package com.madrapps.dagger

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.uast.*

private const val COMPONENT = "dagger.Component"
private const val COMPONENT_BUILDER = "dagger.Component.Builder"
private const val COMPONENT_FACTORY = "dagger.Component.Factory"
private const val MODULE = "dagger.Module"

val UClass.isAbstract: Boolean
    get() = PsiUtil.isAbstractClass(javaPsi)

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

fun UAnnotation.modules(): List<UClassLiteralExpression> {
    val modules = findAttributeValue("modules")
    if (modules is UClassLiteralExpression) return listOf(modules)
    if (modules is UCallExpression) return modules.valueArguments.mapNotNull { it as? UClassLiteralExpression }
    return emptyList()
}

fun PsiClass.toUClass(): UClass? = toUElement() as? UClass

fun PsiClassType.toUClass(): UClass? = resolve()?.toUClass()