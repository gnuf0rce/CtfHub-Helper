package io.github.gnuf0rce.ctfhub.data

import java.time.*

interface Event {
    val id: Long
    val title: String
    val start: OffsetDateTime
    val end: OffsetDateTime
}