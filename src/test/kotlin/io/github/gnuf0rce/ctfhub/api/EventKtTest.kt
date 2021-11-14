package io.github.gnuf0rce.ctfhub.api

import io.github.gnuf0rce.ctfhub.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*

internal class EventKtTest : CtfHubClientTest() {

    @Test
    fun running(): Unit = runBlocking {
        client.getRunning()
    }

    @Test
    fun upcoming(): Unit = runBlocking {
        client.getUpcoming()
    }

    @Test
    fun info(): Unit = runBlocking {
        var total = -1
        for (offset in 0 until 999 step DEFAULT_LIMIT) {
            if (offset >= total) break
            val data = client.getAll(offset = offset, limit = DEFAULT_LIMIT)
            println(data.offset)
            total = data.total
//            for (event in client.getAll().items) {
//                println(client.getInfo(id = event.id))
//            }
        }
    }
}