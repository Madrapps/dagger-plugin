package com.madrapps.dagger

import com.madrapps.daggerprocessor.GenerateTest

@GenerateTest(directory = "inject", exclude = ["assets"])
abstract class InjectTestCase : BaseTestCase("inject")