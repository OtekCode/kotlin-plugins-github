package pl.otekplay.farmer

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("FarmerPlugin",[])
class FarmerPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {


    lateinit var config:FarmerConfig
    lateinit var manager:FarmerManager

    override fun onEnable() {
        config = loadConfig(FarmerConfig::class)
        manager = FarmerManager(this)

        registerCommand(FarmerCommand(this))
        registerListener(FarmerListener(this))
    }

    fun reloadConfig(){
        config = loadConfig(FarmerConfig::class)
        manager.loadFarmers()
        manager.registerRecipes()
    }
}