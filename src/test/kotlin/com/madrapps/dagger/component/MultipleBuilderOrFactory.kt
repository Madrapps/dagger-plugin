package com.madrapps.dagger.component

class MultipleBuilderOrFactory : ComponentTestCase() {

    fun testComponentWithOneFactory() = testValidation()
    fun testComponentWithOneFactoryFullAnnotation() = testValidation()
    fun testComponentWithOneBuilder() = testValidation()
    fun testComponentWithOneBuilderFullAnnotation() = testValidation()

    fun testComponentWithTwoFactory() = testValidation()
    fun testComponentWithTwoBuilder() = testValidation()
    fun testComponentWithOneFactoryOneBuilder() = testValidation()

    fun testKSuccessComponent() = testValidation()
    fun testKFailureComponent() = testValidation()
}
