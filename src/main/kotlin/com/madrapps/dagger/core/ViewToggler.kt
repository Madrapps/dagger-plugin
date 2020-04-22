package com.madrapps.dagger.core

import com.madrapps.dagger.actions.FullDaggerGraphAction
import com.madrapps.dagger.actions.ViewChildrenAction
import com.madrapps.dagger.actions.ViewParentsAction
import kotlin.String as ID

class ViewToggler {

    private val states: Map<ID, ViewState> = mapOf(
        FullDaggerGraphAction.ID to ViewState(isSelected = false),
        ViewChildrenAction.ID to ViewState(isSelected = false),
        ViewParentsAction.ID to ViewState(isSelected = false)
    )

    fun isSelected(id: ID): Boolean {
        return states[id]?.isSelected ?: error("No action with id=$id")
    }

    fun select(id: ID) {
        states.forEach { (_, u) -> u.isSelected = false }
        states[id]?.isSelected = true
    }

    fun reset() {
        states.forEach { (_, u) -> u.isSelected = false }
        states[FullDaggerGraphAction.ID]?.isSelected = true
    }

    data class ViewState(var isSelected: Boolean)
}
