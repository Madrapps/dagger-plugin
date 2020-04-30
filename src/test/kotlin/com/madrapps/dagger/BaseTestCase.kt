package com.madrapps.dagger

import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.PsiTestUtil
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import com.intellij.util.PathUtil
import dagger.Component
import java.io.File

abstract class BaseTestCase : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = File("src/test", "testData").path

    override fun getProjectDescriptor(): LightProjectDescriptor {
        return object : DefaultLightProjectDescriptor() {
            override fun configureModule(module: Module, model: ModifiableRootModel, contentEntry: ContentEntry) {
                super.configureModule(module, model, contentEntry)
                val daggerJar = File(PathUtil.getJarPathForClass(Component::class.java))
                PsiTestUtil.addLibrary(model, daggerJar.name, daggerJar.parent, daggerJar.name)
            }
        }
    }
}