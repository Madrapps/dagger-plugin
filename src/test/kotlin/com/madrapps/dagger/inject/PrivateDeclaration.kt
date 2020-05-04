package com.madrapps.dagger.inject

class PrivateDeclaration : InjectTestCase() {

    fun testPrimaryConstructor() = testValidation()
    fun testPublicPrimaryConstructor() = testValidation()
    fun testProtectedPrimaryConstructor() = testValidation()
    fun testPrivatePrimaryConstructor() = testValidation()

    fun testKSuccessComponent() = testValidation()
    fun testKFailureComponent() = testValidation()
}