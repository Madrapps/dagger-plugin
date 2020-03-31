package com.madrapps.dagger.spi;

import com.squareup.javapoet.ClassName;
import dagger.model.BindingGraph;
import dagger.spi.BindingGraphPlugin;
import dagger.spi.DiagnosticReporter;

import javax.lang.model.element.TypeElement;
import java.util.stream.Collectors;


public class SpiPlugin implements BindingGraphPlugin {

    @Override
    public void visitGraph(BindingGraph bindingGraph, DiagnosticReporter diagnosticReporter) {
        TypeElement componentElement =
                bindingGraph.rootComponentNode().componentPath().currentComponent();
        ClassName componentName = ClassName.get(componentElement);
        String dumb = null;
//        dumb.equals(dumb);
        System.out.println(componentName.simpleName());
        for (BindingGraph.Node node : bindingGraph.bindings().stream().distinct().collect(Collectors.toList())) {
            System.out.println(" --- " + node.getClass().getSimpleName());
        }
    }
}
