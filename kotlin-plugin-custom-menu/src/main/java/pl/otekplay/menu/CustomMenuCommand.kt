package pl.otekplay.menu

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand

class CustomMenuCommand(private val plugin: CustomMenuPlugin): PluginCommand() {


    @CommandAlias("menureload")
    @CommandPermission("command.menureload")
    fun onCommand(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.youReloadedMenus)
        plugin.loadMenu()
    }
}