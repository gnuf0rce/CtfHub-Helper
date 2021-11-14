package io.github.gnuf0rce.ctfhub.data

sealed interface Entry {
    val offset: Int
    val limit: Int
}