package pl.otekplay.menu

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("CustomMenuPlugin",[])
class CustomMenuPlugin(
        pluginLoader: PluginLoader,
                       annotation: PluginAnnotation,
                       logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config:CustomMenuConfig
    lateinit var manager: CustomMenuManager

    override fun onEnable() {
        config = loadConfig(CustomMenuConfig::class)
        manager = CustomMenuManager(this)
        registerListener(CustomMenuListener(this))
        registerCommand(CustomMenuCommand(this))
    }

    fun loadMenu(){
        config = loadConfig(CustomMenuConfig::class)
        manager.loadMenus()
    }
}