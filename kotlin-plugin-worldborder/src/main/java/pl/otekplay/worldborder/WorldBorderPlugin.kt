package pl.otekplay.worldborder

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("WorldBorderPlugin",[])
class WorldBorderPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: WorldBorderConfig

    override fun onEnable() {
        config = loadConfig(WorldBorderConfig::class)
        registerCommand(WorldBorderCommand(this))
        registerListener(WorldBorderListener(this))
    }

    fun reloadConfig(){
        config = loadConfig(WorldBorderConfig::class)

    }
}