package com.madrapps.dagger.inject

class ScopeOnConstructor : InjectTestCase() {

    fun testPrimaryConstructor() = testValidation()
    fun testPrimaryConstructorPrimaryScope() = testValidation()
    fun testPrimaryConstructorSingletonScope() = testValidation()

    fun testKSuccessComponent() = testValidation()
    fun testKFailureComponent() = testValidation()
}