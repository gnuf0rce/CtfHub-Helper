package io.github.gnuf0rce.ctfhub.data

import kotlinx.serialization.*

@Serializable
data class EventData(
    @SerialName("items")
    val items: List<EventItem>,
    @SerialName("total")
    val total: Int,
) : Entry {

    @Transient
    override var offset: Int = 0
        internal set

    @Transient
    override var limit: Int = 0
        internal set
}