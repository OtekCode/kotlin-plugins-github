package pl.otekplay.fight

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "FightPlugin", dependency = ["SpawnPlugin"])
class FightPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: FightConfig
    lateinit var manager: FightManager
    override fun onEnable() {
        config = loadConfig(FightConfig::class)
        manager = FightManager(this)
        registerListener(FightListener(this))
        registerCommand(FightCommand(this))
        taskTimerSync(FightTask(this),20)
        FightAPI.plugin = this
    }

    fun reloadConfig(){
        config = loadConfig(FightConfig::class)
    }

}