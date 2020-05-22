package com.madrapps.dagger

import com.madrapps.daggerprocessor.GenerateTest

private const val subDirectory = "binds"

@GenerateTest(directory = subDirectory, exclude = ["assets"])
abstract class BindsTestCase : BaseTestCase(subDirectory)