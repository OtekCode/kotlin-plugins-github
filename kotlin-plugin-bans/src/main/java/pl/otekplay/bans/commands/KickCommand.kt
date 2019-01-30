package pl.otekplay.bans.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.bans.BanPlugin
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep

class KickCommand(val plugin: BanPlugin) : PluginCommand() {

    @CommandAlias("wyrzuc|skickuj|kick")
    @Syntax("<gracz> <powod>")
    @CommandCompletion("@players")
    @CommandPermission("command.wyrzuc")
    fun onCommandKick(sender: CommandSender, @Flags("other") other: Player, reason: String) {
        other.kickPlayer(plugin.config.messages.youGotKicked.joinToString("\n")
                .rep("%reason%", reason)
                .rep("%source%", sender.name))
        val message = plugin.config.messages.playerGotKicked
                .rep("%source%", other.name)
                .rep("%reason%", reason)
                .rep("%source%", sender.name)
        Bukkit.getOnlinePlayers().forEach { it.sendMessage(message) }
    }
}