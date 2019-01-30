package pl.otekplay.spectate

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("duch|obserwator|spectate")
@CommandPermission("command.duch")
class SpectateCommand(private val plugin: SpectatePlugin):PluginCommand() {

    @Subcommand("reload")
    @CommandPermission("command.duch.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}