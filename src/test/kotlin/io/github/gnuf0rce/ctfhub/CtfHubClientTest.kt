package io.github.gnuf0rce.ctfhub

import io.ktor.client.features.*
import io.ktor.utils.io.errors.*

internal open class CtfHubClientTest {
    val client: CtfHubClient by lazy {
        object : CtfHubClient(token = "ctfhub_sessid=ntv7s0eu4199njh1rmj3i02572v37k3e") {
            override val ignore: (Throwable) -> Boolean = { exception ->
                when (exception) {
                    is IOException,
                    is HttpRequestTimeoutException -> {
                        exception.printStackTrace()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
}