package pl.otekplay.generator

import pl.otekplay.plugin.api.PluginConfiguration

class GeneratorConfig:PluginConfiguration {
    val intervalCheckGenerators = 10L
    val messages = GeneratorMessages()
}