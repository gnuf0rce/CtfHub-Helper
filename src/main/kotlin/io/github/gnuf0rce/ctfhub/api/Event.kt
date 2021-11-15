package io.github.gnuf0rce.ctfhub.api

import io.github.gnuf0rce.ctfhub.data.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*

const val PAGE_LIMIT = 10

const val EVENT_ALL = "https://api.ctfhub.com/User_API/Event/getAll"

const val EVENT_RUNNING = "https://api.ctfhub.com/User_API/Event/getRunning"

const val EVENT_UPCOMING = "https://api.ctfhub.com/User_API/Event/getUpcoming"

const val EVENT_INFO = "https://api.ctfhub.com/User_API/Event/getInfo"

private fun HttpRequestBuilder.keys(offset: Int, limit: Int) {
    attributes.put(OFFSET_KEY, offset)
    attributes.put(LIMIT_KEY, limit)
    attributes.put(API_KEY, url.buildString())
}

private fun HttpRequestBuilder.keys() {
    attributes.put(API_KEY, url.buildString())
}

/**
 * title, form, class
 */
typealias Dict = Map<String, String>

suspend fun UseHttpClient.getAll(offset: Int = 0, limit: Int = PAGE_LIMIT, like: Dict = emptyMap()): EventData = json {
    method = HttpMethod.Post
    url(EVENT_ALL)
    keys(offset, limit)

    body = buildJsonObject {
        put("limit", limit)
        put("offset", offset)
        putJsonObject("like") {
            for ((key, value) in like) put(key, value)
        }
    }
    contentType(ContentType.Application.Json)
}

suspend fun UseHttpClient.getRunning(offset: Int = 0, limit: Int = PAGE_LIMIT): EventData = json {
    method = HttpMethod.Post
    url(EVENT_RUNNING)
    keys(offset, limit)

    body = mapOf("limit" to limit, "offset" to offset)
    contentType(ContentType.Application.Json)
}

suspend fun UseHttpClient.getUpcoming(offset: Int = 0, limit: Int = PAGE_LIMIT): EventData = json {
    method = HttpMethod.Post
    url(EVENT_UPCOMING)
    keys(offset, limit)

    body = mapOf("limit" to limit, "offset" to offset)
    contentType(ContentType.Application.Json)
}

suspend fun UseHttpClient.getInfo(id: Long): EventDetail = json {
    method = HttpMethod.Post
    url(EVENT_INFO)
    keys()

    body = mapOf("event_id" to id)
    contentType(ContentType.Application.Json)
}