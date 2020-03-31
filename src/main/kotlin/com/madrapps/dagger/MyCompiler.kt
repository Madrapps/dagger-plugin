package com.madrapps.dagger

import com.intellij.openapi.compiler.CompileScope
import com.intellij.openapi.compiler.Compiler

class MyCompiler : Compiler {

    override fun validateConfiguration(scope: CompileScope?): Boolean {
        println("TTRRTT, $scope")
        return true
    }

    override fun getDescription(): String {
        return "My Custome COmipiler"
    }
}