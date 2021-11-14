package io.github.gnuf0rce.ctfhub.api

import io.github.gnuf0rce.ctfhub.data.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Temp(
    @SerialName("data")
    val data: JsonElement,
    @SerialName("msg")
    val message: String,
    @SerialName("status")
    val status: Boolean
)

val OFFSET_KEY = AttributeKey<Int>("CTFHUB_OFFSET")

val LIMIT_KEY = AttributeKey<Int>("CTFHUB_LIMIT")

val API_KEY = AttributeKey<String>("API_KEY")

suspend inline fun <reified E : Entry> UseHttpClient.json(crossinline block: HttpRequestBuilder.() -> Unit = {}): E {
    val builder = HttpRequestBuilder().apply(block)
    val temp = useHttpClient { client ->
        client.request<Temp>(builder)
    }
    check(temp.status) { temp.message }
    val entry = UseHttpClient.Json.decodeFromJsonElement<E>(temp.data)

    entry.offset = builder.attributes.getOrNull(OFFSET_KEY) ?: 0
    entry.limit = builder.attributes.getOrNull(LIMIT_KEY) ?: 0
    entry.api = builder.attributes.getOrNull(API_KEY) ?: ""

    return entry
}