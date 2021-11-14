package io.github.gnuf0rce.mirai.plugin.command

import io.github.gnuf0rce.ctfhub.api.*
import io.github.gnuf0rce.mirai.plugin.*
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.message.data.*

object CtfHubEventCommand : CompositeCommand(
    owner = CtfHubHelperPlugin,
    "ctf-event",
    description = "CTFHUB EVENT with https://www.ctfhub.com/#/calendar"
) {

    @SubCommand("running")
    suspend fun CommandSenderOnMessage<*>.running() {
        sendMessage(
            try {
                ctfhub.getRunning().toMessage()
            } catch (cause: Throwable) {
                "出现错误: $cause".toPlainText()
            }
        )
    }

    @SubCommand("upcoming")
    suspend fun CommandSenderOnMessage<*>.upcoming() {
        sendMessage(
            try {
                ctfhub.getUpcoming().toMessage()
            } catch (cause: Throwable) {
                "出现错误: $cause".toPlainText()
            }
        )
    }
}