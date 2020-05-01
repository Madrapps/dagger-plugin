package com.madrapps.dagger.component

class NoModuleAnnotation : ComponentTestCase() {

    fun testComponentWithOneEmptyModuleOneNormalClass() = testValidation()
    fun testComponentWithOneModule() = testValidation()
    fun testComponentWithOneNormalClass() = testValidation()
    fun testComponentWithOneNormalClassOneEmptyModule() = testValidation()
    fun testComponentWithSingleClass() = testValidation()
    fun testComponentWithSingleModule() = testValidation()
    fun testComponentWithTwoModule() = testValidation()
    fun testComponentWithTwoNormalClass() = testValidation()
    
    fun testKSuccessComponent() = testValidation()
    fun testKFailureComponent() = testValidation()
}
