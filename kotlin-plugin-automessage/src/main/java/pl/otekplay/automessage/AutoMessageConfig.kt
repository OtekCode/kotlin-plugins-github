package pl.otekplay.automessage

import pl.otekplay.plugin.api.PluginConfiguration

class AutoMessageConfig : PluginConfiguration {
    val secondsBeetwenMessages = 60L
    val messages = AutoMessageMessages()
}

