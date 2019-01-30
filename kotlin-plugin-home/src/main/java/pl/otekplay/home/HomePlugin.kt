package pl.otekplay.home

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "HomePlugin",dependency = [ "DatabasePlugin", "TeleportPlugin", "GuildPlugin" ])
class HomePlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: HomeConfig
    lateinit var manager: HomeManager

    override fun onEnable() {
        config = loadConfig(HomeConfig::class)
        manager = HomeManager(this)
        registerListener(HomeListener(this))
        registerCommand(HomeCommand(this))
    }

    fun reloadConfig(){
        config = loadConfig(HomeConfig::class)
    }
}