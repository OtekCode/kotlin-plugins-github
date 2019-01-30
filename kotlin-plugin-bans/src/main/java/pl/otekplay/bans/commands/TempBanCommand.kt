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
import pl.otekplay.plugin.util.Dates

class TempBanCommand(private val plugin: BanPlugin) : PluginCommand() {

    @CommandAlias("tempbanip")
    @Syntax("<gracz> <czas> <powod>")
    @CommandCompletion("@players")
    @CommandPermission("command.tempbanip")
    fun onCommandTempBanIP(
            sender: CommandSender,
            @Flags("other")
            p: Player,
            time: String,
            reason: String
    ) {
        val banIp = plugin.manager.banIp(p, sender.name, Dates.parseString(time), reason)
        sender.sendMessage(banIp.replaceString(plugin.config.messages.youTempBanPlayer).rep("%name%", p.name))
        Bukkit.broadcastMessage(banIp.replaceString(plugin.config.messages.playerGotBan).rep("%name%", p.name))
        plugin.manager.kickPlayerAfterBan(p.player, banIp)

    }

    @CommandAlias("tempban")
    @Syntax("<gracz> <czas> <powod>")
    @CommandCompletion("@players")
    @CommandPermission("command.tempban")
    fun onCommandTempBanID(
            sender: CommandSender,
            @Flags("other")
            op: OfflinePlayer,
            time: String,
            reason: String
    ) {
        if(plugin.manager.getBan(op.uniqueId.toString(), BanType.PLAYER) != null) return sender.sendMessage(plugin.config.messages.playerHasAlreadyBanID.rep("%name%",op.name))
        val banId = plugin.manager.banId(op, sender.name, Dates.parseString(time), reason)
        sender.sendMessage(banId.replaceString(plugin.config.messages.youTempBanPlayer).rep("%name%", op.name))
        Bukkit.broadcastMessage(banId.replaceString(plugin.config.messages.playerGotBan).rep("%name%", op.name))
        if (op.isOnline) plugin.manager.kickPlayerAfterBan(op.player, banId)
    }


}