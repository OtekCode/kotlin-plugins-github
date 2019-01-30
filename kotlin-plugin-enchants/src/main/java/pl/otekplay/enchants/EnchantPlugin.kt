package pl.otekplay.enchants

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("EnchantPlugin", [])
class EnchantPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: EnchantConfig
    override fun onEnable() {
        config = loadConfig(EnchantConfig::class)
        registerListener(EnchantListener(this))
        registerCommand(EnchantCommand(this))
    }
}