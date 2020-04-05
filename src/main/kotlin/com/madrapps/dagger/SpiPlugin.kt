package com.madrapps.dagger

import dagger.model.BindingGraph
import dagger.spi.BindingGraphPlugin
import dagger.spi.DiagnosticReporter

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

        Presenter.addBindings(bindingGraph)
    }
}