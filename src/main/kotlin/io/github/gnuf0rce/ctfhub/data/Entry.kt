package io.github.gnuf0rce.ctfhub.data

sealed interface Entry {
    var api: String
    var offset: Int
    var limit: Int
}