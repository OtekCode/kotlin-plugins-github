package pl.otekplay.kit

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("KitPlugin", ["DatabasePlugin"])
class KitPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config:KitConfig
    lateinit var manager:KitManager
    lateinit var menu:KitMenu

    override fun onEnable() {
        loadConfig()
        manager = KitManager(this)
        menu = KitMenu(this)
        registerListener(KitListener(this))
        registerCommand(KitCommand(this))

    }

    fun loadConfig(){
        config = loadConfig(KitConfig::class)
    }
}