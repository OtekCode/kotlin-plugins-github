package pl.otekplay.mysterybox

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "MysteryBoxPlugin", dependency = [])
class MysteryBoxPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config:MysteryBoxConfig
    lateinit var manager:MysteryBoxManager

    override fun onEnable() {
        config = loadConfig(MysteryBoxConfig::class)
        manager = MysteryBoxManager(this)
        registerCommand(MysteryBoxCommand(this))
        registerListener(MysteryBoxListener(this))

    }
}