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
        println("RootComponent - $rootComponentNode")
        val componentElement =
            rootComponentNode.componentPath().currentComponent()
        val componentName = ClassName.get(componentElement)
        val dumb: String? = null
        //        dumb.equals(dumb);
        //println(" - |-| - " + componentName.simpleName())
        val map = bindingGraph.bindings().map { it.key().type() }
        println("Keys = $map")
        for (node in bindingGraph.bindings().stream().distinct().collect(Collectors.toList())) {
            //println(" --- " + node.javaClass.simpleName)
        }

        Presenter.updateView(bindingGraph.bindings().map { it.key().toString() })
    }
}