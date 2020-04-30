package com.madrapps.dagger.component

import com.madrapps.dagger.BaseTestCase

class InterfaceOrAbstractTest : BaseTestCase() {

    fun testClassComponent() {
        val testName = getTestName(false)
        myFixture.configureByFile("component/interfaceOrAbstract/$testName.java")
        myFixture.testHighlighting()
    }

    fun testKClassComponent() {
        val testName = getTestName(false)
        myFixture.configureByFile("component/interfaceOrAbstract/$testName.kt")
        myFixture.testHighlighting()
    }
}