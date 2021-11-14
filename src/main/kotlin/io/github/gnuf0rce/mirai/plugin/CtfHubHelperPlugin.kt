package io.github.gnuf0rce.mirai.plugin

import io.github.gnuf0rce.mirai.plugin.command.*
import io.github.gnuf0rce.mirai.plugin.data.*
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.*

object CtfHubHelperPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "io.github.gnuf0rce.ctfhub-helper",
        name = "ctfhub-helper",
        version = "1.0.0",
    ) {
        author("cssxsh")
    }
) {

    override fun onEnable() {
        CtfHubConfig.reload()
        CtfHubEventCommand.register()
    }

    override fun onDisable() {
        CtfHubEventCommand.unregister()
    }
}