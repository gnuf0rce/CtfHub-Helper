package io.github.gnuf0rce.ctfhub.data

import kotlinx.serialization.*
import java.time.*

@Serializable
data class EventItem(
    @SerialName("id")
    override val id: Long,
    @SerialName("title")
    override val title: String,
    @SerialName("form")
    val form: String? = null,
    @SerialName("class")
    val category: String? = null,
    @Contextual
    @SerialName("start_time")
    override val start: OffsetDateTime,
    @Contextual
    @SerialName("end_time")
    override val end: OffsetDateTime,
    @SerialName("official_url")
    val official: String? = null,
) : Event