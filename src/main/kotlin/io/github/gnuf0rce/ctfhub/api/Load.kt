package io.github.gnuf0rce.ctfhub.api

import io.github.gnuf0rce.ctfhub.data.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.reflect.*
import kotlin.reflect.full.*

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

suspend inline fun <reified E : Entry> UseHttpClient.json(crossinline block: HttpRequestBuilder.() -> Unit = {}): E {
    var offset = 0
    var limit = 0
    val temp = useHttpClient<Temp> { client ->
        val builder = HttpRequestBuilder().apply(block)
        offset = builder.attributes.getOrNull(OFFSET_KEY) ?: 0
        limit = builder.attributes.getOrNull(LIMIT_KEY) ?: 0
        client.request(builder)
    }
    check(temp.status) { temp.message }
    val entry = UseHttpClient.Json.decodeFromJsonElement<E>(temp.data)

    @Suppress("UNCHECKED_CAST")
    (E::class.memberProperties.find { it.name == "offset" } as? KMutableProperty1<E, Int>)?.set(entry, offset)
    @Suppress("UNCHECKED_CAST")
    (E::class.memberProperties.find { it.name == "limit" } as? KMutableProperty1<E, Int>)?.set(entry, limit)

    return entry
}