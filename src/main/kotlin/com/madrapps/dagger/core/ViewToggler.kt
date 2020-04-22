package com.madrapps.dagger.core

import com.madrapps.dagger.actions.FullDaggerGraphAction
import com.madrapps.dagger.actions.ViewChildrenAction
import com.madrapps.dagger.actions.ViewParentsAction
import kotlin.String as ID

class ViewToggler {

    private val states: Map<ID, ViewState> = mapOf(
        FullDaggerGraphAction.ID to ViewState(isSelected = false, isEnabled = false),
        ViewChildrenAction.ID to ViewState(isSelected = false, isEnabled = false),
        ViewParentsAction.ID to ViewState(isSelected = false, isEnabled = false)
    )

    fun state(id: ID): ViewState {
        return states[id] ?: error("No action with id=$id")
    }

    fun select(id: ID) {
        states.forEach { (_, u) -> u.isSelected = false }
        states[id]?.isSelected = true
    }

    data class ViewState(var isSelected: Boolean, var isEnabled: Boolean)
}
