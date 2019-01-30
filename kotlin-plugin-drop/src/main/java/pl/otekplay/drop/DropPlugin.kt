package pl.otekplay.drop

import pl.otekplay.drop.commands.DropCommand
import pl.otekplay.drop.commands.LevelCommand
import pl.otekplay.drop.commands.TurboCommand
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.tab.TabAPI
import java.util.logging.Logger

@PluginAnnotation(name = "DropPlugin", dependency = ["DatabasePlugin"])
class DropPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: DropConfig
    lateinit var manager: DropManager
    lateinit var menu: DropMenu

    override fun onEnable() {
        config = loadConfig(DropConfig::class)
        manager = DropManager(this)
        menu = DropMenu(this)
        registerCommand(DropCommand(this))
        registerCommand(TurboCommand(this))
        registerCommand(LevelCommand(this))
        registerListener(DropListener(this))
        registerVariables()
    }

    fun registerVariables() {
        TabAPI.registerVariable("droplevel") { p ->
            manager.getUser(p.uniqueId)?.level.toString()
        }
        TabAPI.registerVariable("biome") { p ->
            p.location.block.biome.name
        }
    }

    fun reloadConfig() {
        config = loadConfig(DropConfig::class)
        manager.loadDrops()
    }
}