package pl.otekplay.spectate

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger


@PluginAnnotation("SpectatePlugin", ["SpawnPlugin"])
class SpectatePlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: SpectateConfig
    override fun onEnable() {
        config = loadConfig(SpectateConfig::class)
        registerListener(SpectateListener(this))
        registerCommand(SpectateCommand(this))
    }

    fun reloadConfig(){
        config = loadConfig(SpectateConfig::class)

    }
}