package pl.otekplay.antyfavoring

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "AntyFavoring", dependency = [])
class AntyFavoringPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: AntyFavoringConfig
    override fun onEnable() {
        config = loadConfig(AntyFavoringConfig::class)
        registerCommand(AntyFavoringCommand(this))
        registerListener(AntyFavoringListener(this))
    }

    fun reloadConfig(){
        config = loadConfig(AntyFavoringConfig::class)
    }
}