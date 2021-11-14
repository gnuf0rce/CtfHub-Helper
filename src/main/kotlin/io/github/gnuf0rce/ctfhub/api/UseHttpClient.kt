package io.github.gnuf0rce.ctfhub.api

import io.ktor.client.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.modules.*
import java.time.*

interface UseHttpClient {
    suspend fun <T> useHttpClient(block: suspend (HttpClient) -> T): T


    companion object {

        object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor(OffsetDateTime::class.qualifiedName!!, PrimitiveKind.LONG)

            override fun deserialize(decoder: Decoder): OffsetDateTime {
                return OffsetDateTime.ofInstant(Instant.ofEpochSecond(decoder.decodeLong()), ZoneId.systemDefault())
            }

            override fun serialize(encoder: Encoder, value: OffsetDateTime) {
                encoder.encodeLong(value.toEpochSecond())
            }
        }

        val Json = kotlinx.serialization.json.Json {
            prettyPrint = true
            // XXX: ignoreUnknownKeys = true
            isLenient = true
            allowStructuredMapKeys = true
            serializersModule = SerializersModule {
                include(serializersModule)
                contextual(OffsetDateTimeSerializer)
            }
        }

        const val DefaultTimeout: Long = 30 * 1000L
    }
}