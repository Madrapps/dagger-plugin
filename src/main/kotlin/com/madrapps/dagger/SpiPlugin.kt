package com.madrapps.dagger

import com.intellij.openapi.project.Project
import com.madrapps.dagger.services.log
import com.madrapps.dagger.services.service
import dagger.model.BindingGraph
import dagger.spi.BindingGraphPlugin
import dagger.spi.DiagnosticReporter

class SpiPlugin(private val project: Project) : BindingGraphPlugin {

    override fun visitGraph(
        bindingGraph: BindingGraph,
        diagnosticReporter: DiagnosticReporter
    ) {
        val rootComponentNode = bindingGraph.rootComponentNode()
        project.log("------------------------------------------------------------")
        project.log("RootComponent - $rootComponentNode")
        val componentNodes = bindingGraph.componentNodes()
        project.log("ComponentNodes - ${componentNodes.size} - $componentNodes")
        project.log("Binding Keys - ${bindingGraph.bindings().map { it.key() }}")
        project.log("------------------------------------------------------------\n")

        project.service.addBindings(bindingGraph)
    }
}