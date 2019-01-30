package pl.otekplay.trade

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "TradePlugin",dependency = ["SpawnPlugin"])
class TradePlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger
) : Plugin(pluginLoader, annotation, logger) {


    lateinit var config: TradeConfig
    override fun onEnable() {
        config = loadConfig(TradeConfig::class)
        registerListener(TradeListener(this))
        registerCommand(TradeCommand(this))

    }


    fun reloadConfig(){
        config = loadConfig(TradeConfig::class)

    }
}