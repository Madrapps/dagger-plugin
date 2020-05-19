package com.madrapps.dagger.utils

val List<String>.presentable
    get() = "[${this.joinToString(", ")}]"