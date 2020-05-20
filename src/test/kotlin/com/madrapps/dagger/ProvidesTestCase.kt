package com.madrapps.dagger

import com.madrapps.daggerprocessor.GenerateTest

private const val subDirectory = "provides"

@GenerateTest(directory = subDirectory, exclude = ["assets"])
abstract class ProvidesTestCase : BaseTestCase(subDirectory)