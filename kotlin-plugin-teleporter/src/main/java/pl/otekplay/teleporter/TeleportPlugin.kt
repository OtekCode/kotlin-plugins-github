package pl.otekplay.teleporter

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.teleporter.commands.TeleportAcceptCommand
import pl.otekplay.teleporter.commands.TeleportAskCommand
import pl.otekplay.teleporter.commands.TeleportCommand
import pl.otekplay.teleporter.commands.TeleportReloadCommand
import java.util.logging.Logger

@PluginAnnotation(name = "TeleportPlugin",dependency = [])
class TeleportPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: TeleportConfig
    lateinit var teleporter:Teleporter
    override fun onEnable() {
        config = loadConfig(TeleportConfig::class)
        teleporter = Teleporter(this)
        registerCommand(TeleportCommand(this))
        registerCommand(TeleportAskCommand(this))
        registerCommand(TeleportAcceptCommand(this))
        registerCommand(TeleportReloadCommand(this))
        teleporter.start()
        TeleportAPI.plugin = this
        logger.info("TeleportAPI has been initialized.")
    }


    fun reloadConfig(){
        teleporter.stop()
        config = loadConfig(TeleportConfig::class)
        teleporter = Teleporter(this)
        teleporter.start()
    }
}