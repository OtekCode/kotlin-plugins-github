package pl.otekplay.bans.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Syntax
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import pl.otekplay.bans.BanPlugin
import pl.otekplay.bans.BanType
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep


class UnbanCommand(val plugin: BanPlugin):PluginCommand() {

    @CommandAlias("unbanid")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    @CommandPermission("command.unban")
    fun onCommandUnban(sender: CommandSender, offlinePlayer: OfflinePlayer) {
        val ban = plugin.manager.getBan(offlinePlayer.uniqueId.toString(), BanType.PLAYER)
                ?: return sender.sendMessage(plugin.config.messages.playerIsNotBanned.rep("%name%", offlinePlayer.name))
        plugin.manager.removeBan(ban)
        sender.sendMessage(plugin.config.messages.youUnbannedId.rep("%name%", offlinePlayer.name))
    }

    @CommandAlias("unbanip")
    @Syntax("<ip>")
    @CommandPermission("command.unbanip")
    fun onCommandUnbanIp(sender: CommandSender, string: String) {
        val ban = plugin.manager.getBan(string, BanType.IP)
                ?: return sender.sendMessage(plugin.config.messages.ipIsNotBanned.rep("%ip%", string))
        plugin.manager.removeBan(ban)
        sender.sendMessage(plugin.config.messages.youUnbannedIp.rep("%ip%", string))

    }
}