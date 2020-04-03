package com.madrapps.dagger

import com.squareup.javapoet.ClassName
import dagger.model.BindingGraph
import dagger.spi.BindingGraphPlugin
import dagger.spi.DiagnosticReporter
import java.util.stream.Collectors
import javax.annotation.processing.Filer
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

class SpiPlugin : BindingGraphPlugin {

    override fun visitGraph(
        bindingGraph: BindingGraph,
        diagnosticReporter: DiagnosticReporter
    ) {
        val rootComponentNode = bindingGraph.rootComponentNode()
        println("------------------------------------------------------------")
        println("RootComponent - $rootComponentNode")
        val componentNodes = bindingGraph.componentNodes()
        println("ComponentNodes - ${componentNodes.size} - $componentNodes")
        println("Binding Keys - ${bindingGraph.bindings().map { it.key() }}")
        println("------------------------------------------------------------\n")

        Presenter.updateView(bindingGraph.bindings().map { it.key().toString() })
    }
}