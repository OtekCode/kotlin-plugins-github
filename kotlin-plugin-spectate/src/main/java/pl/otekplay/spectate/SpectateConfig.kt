package pl.otekplay.spectate

import pl.otekplay.plugin.api.PluginConfiguration

class SpectateConfig:PluginConfiguration {
    val messages = SpectateMessages()
    val spectateTime = 200L
}