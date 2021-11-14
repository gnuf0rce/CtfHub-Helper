package io.github.gnuf0rce.mirai.plugin

import io.github.gnuf0rce.ctfhub.*
import io.github.gnuf0rce.ctfhub.data.*
import io.github.gnuf0rce.mirai.plugin.data.*
import io.ktor.client.features.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.*
import java.io.*


internal const val LOGGER_PROPERTY = "io.github.gnuf0rce.mirai.plugin.logger"

internal const val IMAGE_FOLDER_PROPERTY = "io.github.gnuf0rce.mirai.plugin.image"

internal val logger by lazy {
    val open = System.getProperty(LOGGER_PROPERTY, "${true}").toBoolean()
    if (open) CtfHubHelperPlugin.logger else SilentLogger
}

internal val ImageFolder by lazy {
    val folder = System.getProperty(IMAGE_FOLDER_PROPERTY)
    (if (folder.isNullOrBlank()) CtfHubHelperPlugin.dataFolder else File(folder)).resolve("image")
}

internal val ctfhub by lazy {
    object : CtfHubClient(token = CtfHubConfig.token) {
        init {
            logger.info { "token: ${CtfHubConfig.token}" }
            timeout = CtfHubConfig.timeout * 1000
        }

        override val ignore: (Throwable) -> Boolean = {
            when (it) {
                is IOException,
                is HttpRequestTimeoutException -> {
                    logger.warning { "HttpClient Ignore $it" }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}

fun Entry.toMessage(): Message {
    return when (this) {
        is EventData -> buildMessageChain {
            appendLine("共${total}个赛事, 第${offset / limit + 1}页")
            for (event in items) {
                appendLine("${event.id} ${event.title}")
                appendLine("${event.start.toLocalDate()}~${event.end.toLocalDate()}")
            }
        }
        is EventDetail -> buildMessageChain {
            appendLine("$id $title")
            appendLine("OFFICIAL: $official")
            appendLine("DATE: ${start.toLocalDate()}~${end.toLocalDate()}")
            appendLine("CATEGORY: $category $form")
            appendLine(details)
        }
    }
}

