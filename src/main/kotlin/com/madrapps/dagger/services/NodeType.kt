package com.madrapps.dagger.services

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.madrapps.dagger.services.NodeType.*
import dagger.model.BindingGraph
import dagger.model.BindingKind
import javax.swing.Icon

enum class NodeType(val icon: Icon) {
    COMPONENT(AllIcons.Nodes.Class),
    SUBCOMPONENT(AllIcons.Nodes.Static),
    MULTIBOUND(AllIcons.Nodes.Target),
    INJECT(AllIcons.Nodes.Interface),
    PROVIDES(AllIcons.Nodes.Property),
    BINDS(IconLoader.getIcon("/icons/binds.svg")),
    MEMBER_INJECTION(AllIcons.Nodes.AbstractException),
    UNKNOWN(AllIcons.Nodes.AnonymousClass)
}

fun BindingKind.toNodeType(): NodeType {
    return when {
        this.isMultibinding -> MULTIBOUND
        this == BindingKind.INJECTION -> INJECT
        this == BindingKind.PROVISION -> PROVIDES
        this == BindingKind.DELEGATE || this == BindingKind.BOUND_INSTANCE -> BINDS
        this == BindingKind.MEMBERS_INJECTION -> MEMBER_INJECTION
        else -> UNKNOWN
    }
}

fun BindingGraph.ComponentNode.toNodeType(): NodeType {
    return if (isSubcomponent) SUBCOMPONENT else COMPONENT
}