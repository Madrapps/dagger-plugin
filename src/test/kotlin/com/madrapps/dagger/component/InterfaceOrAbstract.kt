package com.madrapps.dagger.component

class InterfaceOrAbstract : ComponentTestCase() {

    fun testClassComponent() = testValidationJava()
    fun testKClassComponent() = testValidationKotlin()

    fun testClassComponentWithModule() = testValidationJava(
        "assets/EmptyModule.java"
    )

    fun testKClassComponentWithModule() = testValidationKotlin(
        "assets/EmptyModule.java"
    )
}
