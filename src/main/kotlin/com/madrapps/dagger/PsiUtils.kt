package com.madrapps.dagger

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.util.PsiUtil
import org.jetbrains.uast.*

val UClass.isAbstract: Boolean
    get() = PsiUtil.isAbstractClass(javaPsi)

val UAnnotation.isComponent: Boolean
    get() = qualifiedName == "dagger.Component"

val UAnnotation.isModule: Boolean
    get() = qualifiedName == "dagger.Module"

val UClass.isModule: Boolean
    get() = findAnnotation("dagger.Module") != null

fun UAnnotation.modules(): List<UClassLiteralExpression> {
    val uCallExpression = findAttributeValue("modules") as? UCallExpression
    return uCallExpression?.valueArguments?.mapNotNull {
        it as? UClassLiteralExpression
    } ?: emptyList()
}

fun PsiClass.toUClass(): UClass? = toUElement() as? UClass

fun PsiClassType.toUClass(): UClass? = resolve()?.toUClass()