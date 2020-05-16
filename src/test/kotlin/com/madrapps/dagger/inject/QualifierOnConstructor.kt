package com.madrapps.dagger.inject

class QualifierOnConstructor : InjectTestCase() {

    fun testPrimaryConstructor() = testValidation()
    fun testPrimaryConstructorNamedQualifier() = testValidation()
    fun testPrimaryConstructorPrimaryQualifier() = testValidation()

    fun testKSuccessComponent() = testValidation()
    fun testKFailureComponent() = testValidation()
}