package pl.otekplay.randomteleporter

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("RandomTeleportPlugin", ["TeleportPlugin", "GuildPlugin", "SpawnPlugin"])
class RandomTeleportPlugin(pluginLoader: PluginLoader,
                           annotation: PluginAnnotation,
                           logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: RandomTeleportConfig
    lateinit var manager: RandomTeleportManager


    override fun onEnable() {
        config = loadConfig(RandomTeleportConfig::class)
        manager = RandomTeleportManager(this)
        registerCommand(RandomTeleportCommand(this))
        registerListener(RandomTeleportListener(this))
    }

    fun reloadConfig(){
        config = loadConfig(RandomTeleportConfig::class)

    }
}