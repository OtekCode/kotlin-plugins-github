package pl.otekplay.tab

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger


@PluginAnnotation("TabPlugin", [])
class TabPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: TabConfig
    lateinit var manager: TabManager
    override fun onEnable() {
        config = loadConfig(TabConfig::class)
        manager = TabManager(this)
        TabAPI.plugin = this
        taskTimerAsync(TabUpdate(this),config.updateInterval*20)
        registerListener(TabListener(this))
        registerCommand(TabCommand(this))
    }


    fun reloadConfig(){
        config = loadConfig(TabConfig::class)
        manager.loadSlots()
    }

}
