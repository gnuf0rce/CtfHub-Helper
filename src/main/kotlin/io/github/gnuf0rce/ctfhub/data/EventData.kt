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
    override var api: String = ""

    @Transient
    override var offset: Int = 0

    @Transient
    override var limit: Int = 0
}