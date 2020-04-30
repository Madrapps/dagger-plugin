package com.madrapps.dagger.component

class InterfaceOrAbstract : ComponentTestCase() {

    fun testClassComponent() = testValidationJava()
    fun testClassComponentWithModule() = testValidationJava("assets/EmptyModule.java")
    fun testClassComponentFullAnnotation() = testValidationJava()
    fun testClassComponentWithModuleFullAnnotation() = testValidationJava("assets/EmptyModule.java")

    fun testAbstractClassComponent() = testValidationJava()
    fun testAbstractClassComponentWithModule() = testValidationJava("assets/EmptyModule.java")
    fun testInterfaceComponent() = testValidationJava()
    fun testInterfaceComponentWithModule() = testValidationJava("assets/EmptyModule.java")

    fun testKSuccessComponent() = testValidationKotlin("assets/EmptyModule.java")
    fun testKFailureComponent() = testValidationKotlin("assets/EmptyModule.java")
}
