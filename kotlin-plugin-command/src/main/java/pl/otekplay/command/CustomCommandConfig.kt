package pl.otekplay.command

import pl.otekplay.plugin.api.PluginConfiguration

class CustomCommandConfig : PluginConfiguration {
    val commandAliases = hashMapOf(Pair("ag", "ag2"))
    val messages = CustomCommandMessages()
}