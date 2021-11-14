package io.github.gnuf0rce.ctfhub.api

import io.github.gnuf0rce.ctfhub.data.*
import io.ktor.client.request.*
import io.ktor.http.*

const val PAGE_LIMIT = 10

const val EVENT_ALL = "https://api.ctfhub.com/User_API/Event/getAll"

const val EVENT_RUNNING = "https://api.ctfhub.com/User_API/Event/getRunning"

const val EVENT_UPCOMING = "https://api.ctfhub.com/User_API/Event/getUpcoming"

const val EVENT_INFO = "https://api.ctfhub.com/User_API/Event/getInfo"

suspend fun UseHttpClient.getAll(offset: Int = 0, limit: Int = PAGE_LIMIT): EventData = json {
    method = HttpMethod.Post
    url(EVENT_ALL)
    attributes.put(OFFSET_KEY, offset)
    attributes.put(LIMIT_KEY, limit)
    attributes.put(API_KEY, EVENT_ALL)

    body = mapOf("limit" to limit, "offset" to offset)
    contentType(ContentType.Application.Json)
}

suspend fun UseHttpClient.getRunning(offset: Int = 0, limit: Int = PAGE_LIMIT): EventData = json {
    method = HttpMethod.Post
    url(EVENT_RUNNING)
    attributes.put(OFFSET_KEY, offset)
    attributes.put(LIMIT_KEY, limit)

    body = mapOf("limit" to limit, "offset" to offset)
    contentType(ContentType.Application.Json)
}

suspend fun UseHttpClient.getUpcoming(offset: Int = 0, limit: Int = PAGE_LIMIT): EventData = json {
    method = HttpMethod.Post
    url(EVENT_UPCOMING)
    attributes.put(OFFSET_KEY, offset)
    attributes.put(LIMIT_KEY, limit)
    attributes.put(API_KEY, EVENT_UPCOMING)

    body = mapOf("limit" to limit, "offset" to offset)
    contentType(ContentType.Application.Json)
}

suspend fun UseHttpClient.getInfo(id: Long): EventDetail = json {
    method = HttpMethod.Post
    url(EVENT_INFO)
    attributes.put(API_KEY, EVENT_INFO)

    body = mapOf("event_id" to id)
    contentType(ContentType.Application.Json)
}