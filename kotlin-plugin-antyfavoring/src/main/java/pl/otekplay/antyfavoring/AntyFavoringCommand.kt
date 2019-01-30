package pl.otekplay.antyfavoring

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("faworka|antyfavoring")
@CommandPermission("command.faworka")
class AntyFavoringCommand(private val plugin: AntyFavoringPlugin) : PluginCommand() {


    @Subcommand("reload")
    @CommandPermission("command.faworka.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)

    }
}