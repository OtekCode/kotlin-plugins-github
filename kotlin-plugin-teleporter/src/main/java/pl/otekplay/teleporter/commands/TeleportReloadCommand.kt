package pl.otekplay.teleporter.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.teleporter.TeleportPlugin

@CommandAlias("teleport")
@CommandPermission("command.teleport")
class TeleportReloadCommand(val plugin: TeleportPlugin): PluginCommand() {


    @Subcommand("reload")
    @CommandPermission("command.teleport.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}