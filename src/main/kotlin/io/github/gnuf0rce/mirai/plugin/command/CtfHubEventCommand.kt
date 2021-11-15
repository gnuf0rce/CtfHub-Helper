package io.github.gnuf0rce.mirai.plugin.command

import io.github.gnuf0rce.ctfhub.api.*
import io.github.gnuf0rce.mirai.plugin.*
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.*

object CtfHubEventCommand : CompositeCommand(
    owner = CtfHubHelperPlugin,
    "ctf-event",
    description = "CTFHUB EVENT with https://www.ctfhub.com/#/calendar"
) {

    @SubCommand("running")
    suspend fun CommandSenderOnMessage<*>.running(page: Int = 1) {
        logger.info { "running" }
        sendMessage(
            try {
                ctfhub.getRunning(offset = (page - 1) * PAGE_LIMIT).toMessage()
            } catch (cause: Throwable) {
                "出现错误: $cause".toPlainText()
            }
        )
    }

    @SubCommand("upcoming")
    suspend fun CommandSenderOnMessage<*>.upcoming(page: Int = 1) {
        logger.info { "upcoming" }
        sendMessage(
            try {
                ctfhub.getUpcoming(offset = (page - 1) * PAGE_LIMIT).toMessage()
            } catch (cause: Throwable) {
                "出现错误: $cause".toPlainText()
            }
        )
    }

    @SubCommand("info")
    suspend fun CommandSenderOnMessage<*>.info(id: Long) {
        logger.info { "info" }
        sendMessage(
            try {
                ctfhub.getInfo(id = id).toMessage()
            } catch (cause: Throwable) {
                "出现错误: $cause".toPlainText()
            }
        )
    }

    @SubCommand("search")
    suspend fun CommandSenderOnMessage<*>.search(title: String) {
        logger.info { "search" }
        sendMessage(
            try {
                ctfhub.getAll(like = mapOf("title" to title)).toMessage()
            } catch (cause: Throwable) {
                "出现错误: $cause".toPlainText()
            }
        )
    }
}