package com.madrapps.dagger.component

class InterfaceOrAbstract : ComponentTestCase() {

    fun testClassComponent() = testValidation()
    fun testClassComponentWithModule() = testValidation()
    fun testClassComponentFullAnnotation() = testValidation()
    fun testClassComponentWithModuleFullAnnotation() = testValidation()

    fun testAbstractClassComponent() = testValidation()
    fun testAbstractClassComponentWithModule() = testValidation()
    fun testInterfaceComponent() = testValidation()
    fun testInterfaceComponentWithModule() = testValidation()

    fun testKSuccessComponent() = testValidation()
    fun testKFailureComponent() = testValidation()
}
