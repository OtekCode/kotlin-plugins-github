package pl.otekplay.home

import pl.otekplay.plugin.api.PluginConfiguration

class HomeConfig : PluginConfiguration {
    val timeTeleportHome = 10000L
    val messages = HomeMessages()
    val homeDefaultName = "default"
    val homesLimit = listOf(
            ConfigHomeLimit(1,"home.vip",2),
            ConfigHomeLimit(3,"home.svip",3)
    )
    val defaultLimitHome = 1
}