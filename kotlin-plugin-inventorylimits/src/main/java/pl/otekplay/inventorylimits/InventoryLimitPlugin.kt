package pl.otekplay.inventorylimits

import pl.otekplay.inventorylimits.deposit.DepositManager
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "InventoryLimitPlugin",dependency = ["DatabasePlugin"])
class InventoryLimitPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: InventoryLimitConfig
    lateinit var manager:DepositManager
    override fun onEnable() {
        config = loadConfig(InventoryLimitConfig::class)
        manager = DepositManager(this)
        registerListener(InventoryLimitListener(this))
        registerCommand(InventoryLimitCommand(this))
    }

    fun reloadConfig(){
        config = loadConfig(InventoryLimitConfig::class)
    }
}