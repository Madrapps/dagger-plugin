package com.madrapps.dagger

import com.intellij.psi.util.PsiUtil
import org.jetbrains.uast.UClass

val UClass.isAbstract: Boolean
    get() = PsiUtil.isAbstractClass(javaPsi)