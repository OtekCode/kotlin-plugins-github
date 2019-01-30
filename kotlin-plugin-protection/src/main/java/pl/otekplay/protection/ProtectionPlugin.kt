package pl.otekplay.protection

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "ProtectionPlugin", dependency = ["TagPlugin"])
class ProtectionPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: ProtectionConfig
    lateinit var manager: ProtectionManager
    override fun onEnable() {

        config = loadConfig(ProtectionConfig::class)
        manager = ProtectionManager(this)
        registerCommand(ProtectionCommand(this))
        registerListener(ProtectionListener(this))

    }


    fun reloadConfig(){
        config = loadConfig(ProtectionConfig::class)

    }
}