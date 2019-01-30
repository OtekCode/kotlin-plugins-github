package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class JoinCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("dolacz|join")
    @Syntax("<tag>")
    @CommandPermission("command.guild.join")
    fun onCommand(player: Player, tag: String) {
        if (manager.getGuildByMember(player.uniqueId) != null) return player.sendMessage(config.messages.youAlreadyGotGuild)
        val guild = manager.getGuildByTag(tag)
                ?: return player.sendMessage(config.messages.cantFindGuildWithTag.rep("%tag%", tag))
        if (! guild.invites.contains(player.uniqueId)) return player.sendMessage(config.messages.youDontHaveInviteFromGuild.rep("%tag%", guild.tag))
        if (! manager.canHaveMoreMembers(guild)) return player.sendMessage(config.messages.youCantJoinGuildIsFull)
        if (! manager.hasJoinItems(guild, player.inventory)) return player.sendMessage(config.messages.youCantJoinGuildNeedItems.rep("%amount%", (guild.members.size * plugin.config.guildJoinPerMemberAmount).toString()))
        manager.removeJoinItems(guild, player.inventory)
        manager.removeInvite(guild, player.uniqueId)
        manager.createGuildMember(guild, player.uniqueId)
        Bukkit.broadcastMessage(config.messages.playerJoinedToGuild.rep("%name%", player.name).rep("%tag%", guild.tag))
    }
}