package com.madrapps.dagger

import com.madrapps.daggerprocessor.GenerateTest

@GenerateTest(directory = "component", exclude = ["assets"])
abstract class ComponentTestCase : BaseTestCase("component")