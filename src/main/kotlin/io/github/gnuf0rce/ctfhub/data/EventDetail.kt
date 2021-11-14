package io.github.gnuf0rce.ctfhub.data

import kotlinx.serialization.*
import java.time.*

@Serializable
data class EventDetail(
    @SerialName("id")
    override val id: Long,
    @SerialName("title")
    override val title: String,
    @SerialName("form")
    val form: String,
    @SerialName("class")
    val category: String,
    @Contextual
    @SerialName("start_time")
    override val start: OffsetDateTime,
    @Contextual
    @SerialName("end_time")
    override val end: OffsetDateTime,
    @SerialName("official_url")
    val official: String,
    @SerialName("details")
    val details: String,
    @SerialName("focus_count")
    val focusCount: Int,
    @SerialName("is_focus")
    val isFocus: Int,// XXX: 0 -> false, 1 -> true
    @SerialName("state")
    val state: Int,// XXX: 0 -> false, 1 -> true
) : Entry, Event {

    @Transient
    override var api: String = ""

    @Deprecated("offset", ReplaceWith("0"))
    @Transient
    override var offset: Int = 0

    @Deprecated("limit", ReplaceWith("0"))
    @Transient
    override var limit: Int = 0
}