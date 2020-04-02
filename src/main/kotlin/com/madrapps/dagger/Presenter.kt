package com.madrapps.dagger

object Presenter {

    private lateinit var daggerView: DaggerView

    fun setView(daggerView: DaggerView) {
        this.daggerView = daggerView
    }

    fun updateView(list: List<String>) {
        daggerView.updateTree(list)
    }
}

interface DaggerView {

    fun updateTree(list: List<String>)
}