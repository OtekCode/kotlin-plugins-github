package pl.otekplay.bans

import pl.otekplay.bans.commands.BanCommand
import pl.otekplay.bans.commands.KickCommand
import pl.otekplay.bans.commands.TempBanCommand
import pl.otekplay.bans.commands.UnbanCommand
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "BanPlugin", dependency = ["DatabasePlugin","GuildPlugin"])
class BanPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: BanConfig
    lateinit var manager: BanManager

    override fun onEnable() {
        config = loadConfig(BanConfig::class)
        manager = BanManager(this)
        registerListener(BanListener(this))

        registerCommand(BanCommand(this))
        registerCommand(KickCommand(this))
        registerCommand(TempBanCommand(this))
        registerCommand(UnbanCommand(this))
    }
}