package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class LeaveCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("opusc|wyjdz|leave")
    @CommandPermission("command.guild.leave")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId) ?: return player.sendMessage(config.messages.youDontHaveGuild)
        val member = guild.getMember(player.uniqueId)!!
        if (guild.isLeader(member.uniqueId)) return player.sendMessage(config.messages.youCantLeaveGuildWhileLeader)
        Bukkit.broadcastMessage(config.messages.playerLeaveFromGuild.rep("%name%", player.name).rep("%tag%", guild.tag))
        manager.removeGuildMember(member.uniqueId)
    }
}