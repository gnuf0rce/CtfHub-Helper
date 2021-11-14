package io.github.gnuf0rce.mirai.plugin.data

import net.mamoe.mirai.console.data.*

object CtfHubConfig : ReadOnlyPluginConfig("CtfHubConfig") {

    @ValueName("github_token")
    @ValueDescription("GitHub Token by https://github.com/settings/tokens")
    val token by value(System.getenv("CTFHUB_TOKEN").orEmpty())

    @ValueName("timeout")
    @ValueDescription("Http Timeout Second")
    val timeout by value(30L)
}