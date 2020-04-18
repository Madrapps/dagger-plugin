package com.madrapps.dagger.markers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.util.concurrency.AppExecutorUtil
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import org.jetbrains.uast.toUElement

class InputMarker : LineMarkerProvider {

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element.project.service.getPsiElements().contains(element.toUElement())) {
            return SubscribeLineMarkerInfo(element)
        }
        return null
    }

}

private class SubscribeLineMarkerInfo(
    psiElement: PsiElement
) : LineMarkerInfo<PsiElement>(
    psiElement,
    psiElement.textRange,
    AllIcons.Actions.Colors,
    null,
    { event, element ->
        ReadAction.nonBlocking {
            element.project.log("Line marker clicked")
        }.inSmartMode(element.project).submit(AppExecutorUtil.getAppExecutorService())
    },
    GutterIconRenderer.Alignment.LEFT
)