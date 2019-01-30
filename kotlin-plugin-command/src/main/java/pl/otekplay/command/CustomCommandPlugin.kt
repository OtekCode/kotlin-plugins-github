package pl.otekplay.command

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger


@PluginAnnotation(name = "CustomCommandPlugin",dependency = [])
class CustomCommandPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config:CustomCommandConfig
    lateinit var manager: CustomCommandManager
    override fun onEnable()     {
        config = loadConfig(CustomCommandConfig::class)
        manager = CustomCommandManager(this)
        registerCommand(CustomCommands(this))
        registerListener(CustomCommandListener(this))

    }

    fun reloadConfig(){
        config = loadConfig(CustomCommandConfig::class)
        manager.loadCommands()
    }
}