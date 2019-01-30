package pl.otekplay.chat.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import pl.otekplay.chat.ChatPlugin
import pl.otekplay.plugin.rep

class BroadcastCommand(plugin: ChatPlugin) : ChatCommand(plugin) {

    @CommandAlias("broadcast|bc")
    @Syntax("<wiadomosc>")
    @CommandPermission("command.broadcast")
    fun onCommandBroadcast(message: String) {
        Bukkit.broadcastMessage(plugin.config.formatBroadcast.rep("%message%", ChatColor.translateAlternateColorCodes('&', message)))

    }

    @CommandAlias("nonprefixedbroadcast|npbc")
    @Syntax("<wiadomosc>")
    @CommandPermission("command.nonprefixedbroadcast")
    fun onCommandNonPrefixedBroadcast(message: String) {
       Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    @CommandAlias("titlebroadcast|tbroadcast|tbc")
    @Syntax("<tytul> <podtytul>")
    @CommandPermission("command.titlebroadcast")
    fun onCommandTitleBroadcast(sender: CommandSender, title: String, subtitle: String) {
        Bukkit.getOnlinePlayers().forEach {
            it.sendTitle(title, subtitle)
        }
    }
}