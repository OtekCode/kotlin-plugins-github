package pl.otekplay.bans.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.bans.BanPlugin
import pl.otekplay.bans.BanType
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep

class BanCommand(private val plugin: BanPlugin) : PluginCommand() {

    @CommandAlias("banip")
    @Syntax("<gracz> <powod>")
    @CommandCompletion("@players")
    @CommandPermission("command.banip")
    fun onCommandPermIP(sender: CommandSender, @Flags("other") p: Player, reason: String) {
        val banIp = plugin.manager.banIp(p, sender.name, - 1, reason)
        sender.sendMessage(banIp.replaceString(plugin.config.messages.youPermBanPlayer).rep("%name%", p.name))
        Bukkit.broadcastMessage(banIp.replaceString(plugin.config.messages.playerGotPermban).rep("%name%", p.name))
        plugin.manager.kickPlayerAfterBan(p.player, banIp)
    }

    @CommandAlias("ban")
    @Syntax("<gracz> <powod>")
    @CommandCompletion("@players")
    @CommandPermission("command.ban")
    fun onCommandPermId(sender: CommandSender, @Flags("other") op: OfflinePlayer, reason: String) {
        if(plugin.manager.getBan(op.uniqueId.toString(), BanType.PLAYER) != null) return sender.sendMessage(plugin.config.messages.playerHasAlreadyBanID.rep("%name%",op.name))
        val banId = plugin.manager.banId(op, sender.name, - 1, reason)
        sender.sendMessage(banId.replaceString(plugin.config.messages.youPermBanPlayer).rep("%name%", op.name))
        Bukkit.broadcastMessage(banId.replaceString(plugin.config.messages.playerGotPermban).rep("%name%", op.name))
        if (op.isOnline) plugin.manager.kickPlayerAfterBan(op.player, banId)
    }

}