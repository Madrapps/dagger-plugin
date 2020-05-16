package com.madrapps.dagger.validation.component

import com.intellij.psi.PsiClassType
import com.madrapps.dagger.utils.isModule
import com.madrapps.dagger.utils.modules
import com.madrapps.dagger.utils.toUClass
import com.madrapps.dagger.validation.Problem.Error
import org.jetbrains.uast.UAnnotation

object NoModuleAnnotation : ComponentProblem() {

    override fun isError(annotation: UAnnotation): List<Error> {
        return annotation.modules().mapNotNull {
            val uClass = (it.type as? PsiClassType)?.toUClass()
            if (uClass?.isModule == false) {
                it.sourcePsi?.let { range ->
                    Error(range, "${uClass.javaPsi.name} is not annotated with @Module")
                }
            } else null
        }
    }
}