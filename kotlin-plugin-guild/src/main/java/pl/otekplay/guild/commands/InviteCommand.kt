package pl.otekplay.guild.commands

import co.aikar.commands.annotation.*
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class InviteCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("zapros|dodaj|invite")
    @CommandCompletion("@players")
    @Syntax("<gracz>")
    @CommandPermission("command.guild.invite")
    fun onCommand(player: Player, @Flags("other") other: Player) {
        val guild = manager.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(config.messages.youDontHaveGuild)
        val inviter = guild.getMember(player.uniqueId) !!
        if (! guild.isLeader(inviter.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        if (! manager.canHaveMoreMembers(guild)) return player.sendMessage(config.messages.yourGuildCantHaveMoreMembers)
        if (manager.getGuildByMember(other.uniqueId) != null) return player.sendMessage(config.messages.playerHasAlreadyGuild.rep("%name%", other.name))
        manager.createInvite(guild, other.uniqueId)
        player.sendMessage(config.messages.youInvitedPlayerToGuild.rep("%name%", other.name))
        if (other.isOnline) other.player.sendMessage(config.messages.youGotInvitedToGuild.rep("%name%", player.name).rep("%tag%", guild.tag))
    }
}