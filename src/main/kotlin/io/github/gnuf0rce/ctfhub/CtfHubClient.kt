package io.github.gnuf0rce.ctfhub

import io.github.gnuf0rce.ctfhub.api.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import java.io.*

open class CtfHubClient(open val token: String) : CoroutineScope, Closeable, UseHttpClient {

    protected var timeout: Long = UseHttpClient.DefaultTimeout

    protected open val client = HttpClient(OkHttp) {
        BrowserUserAgent()
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        Json {
            serializer = KotlinxSerializer(UseHttpClient.Json)
            accept(ContentType.Text.Html)
        }
        defaultRequest {
            header(HttpHeaders.Authorization, token)
            header(HttpHeaders.Host, "api.ctfhub.com")
            header(HttpHeaders.Origin, "https://www.ctfhub.com")
            header(HttpHeaders.Referrer, "https://www.ctfhub.com/")
        }
        engine {
            config {
                // XXX: javax.net.ssl.SSLPeerUnverifiedException: Hostname api.ctfhub.com not verified (no certificates)
                hostnameVerifier { _, _ -> true }
            }
        }
    }

    override val coroutineContext get() = client.coroutineContext

    override fun close() = client.close()

    protected open val ignore: (Throwable) -> Boolean = { it is IOException || it is HttpRequestTimeoutException }

    protected open val maxIgnoreCount = 20

    override suspend fun <T> useHttpClient(block: suspend (HttpClient) -> T): T = supervisorScope {
        var count = 0
        while (isActive) {
            try {
                return@supervisorScope block(client)
            } catch (throwable: Throwable) {
                if (isActive && ignore(throwable)) {
                    if (++count > maxIgnoreCount) {
                        throw throwable
                    }
                } else {
                    throw throwable
                }
            }
        }
        throw CancellationException(null, null)
    }
}