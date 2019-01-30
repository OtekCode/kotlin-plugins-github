package pl.otekplay.tools.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class HelpopCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {


    @CommandAlias("helpop")
    @CommandPermission("command.helpop")
    @Syntax("<wiadomosc>")
    fun onCommandHelpop(sender: Player, message: String) {
        Bukkit.getOnlinePlayers().filter { it.hasPermission(plugin.config.permissionSeeHelpop) }.forEach { it.sendMessage(plugin.config.formatHelpop.rep("%name%",sender.name).rep("%message%",message)) }
        sender.sendMessage(plugin.config.messages.youSendHelpop)
    }
}