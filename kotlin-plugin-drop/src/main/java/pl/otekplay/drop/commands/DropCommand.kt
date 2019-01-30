package pl.otekplay.drop.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.drop.DropPlugin
import pl.otekplay.plugin.api.PluginCommand

class DropCommand(val plugin: DropPlugin) : PluginCommand() {


    @CommandAlias("drop")
    @CommandPermission("command.drop")
    fun onCommandDrop(player: Player) {
        plugin.menu.open(player)
    }

    @CommandAlias("dropreload")
    @CommandPermission("command.drop.reload")
    fun onCommandDropReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.dropsHasBeenReloaded)
    }



}