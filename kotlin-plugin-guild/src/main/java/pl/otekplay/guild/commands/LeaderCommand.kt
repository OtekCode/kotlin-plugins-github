package pl.otekplay.guild.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class LeaderCommand(plugin: GuildPlugin) : GuildCommand(plugin) {
    @Subcommand("lider|leader")
    @CommandPermission("command.guild.leader")
    @Syntax("<gracz>")
    fun onCommand(player: Player, @Flags("other") other: OfflinePlayer) {
        val guild = manager.getGuildByMember(player.uniqueId)
        if (guild == null) {
            player.sendMessage(config.messages.youDontHaveGuild)
            return
        }
        val member = guild.getMember(player.uniqueId)!!
        if (!guild.isLeader(member.uniqueId)) {
            player.sendMessage(config.messages.youAreNotLeader)
            return
        }
        val otherMember = guild.getMember(other.uniqueId)
        if (otherMember == null) {
            player.sendMessage(config.messages.playerIsNotInYourGuild.rep("%name%", other.name))
            return
        }
        manager.changeLeader(guild,other.uniqueId)
        Bukkit.broadcastMessage(config.messages.guildLeaderHasBeenChanged.rep("%name%",other.name).rep("%tag%",guild.tag))
    }
}