package com.madrapps.dagger

import com.madrapps.daggerprocessor.GenerateTest

private const val subDirectory = "inject"

@GenerateTest(directory = subDirectory, exclude = ["assets"])
abstract class InjectTestCase : BaseTestCase(subDirectory)