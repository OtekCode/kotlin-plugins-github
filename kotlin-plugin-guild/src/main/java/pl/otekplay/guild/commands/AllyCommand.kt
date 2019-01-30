package pl.otekplay.guild.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class AllyCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("sojusz|ally")
    @CommandPermission("command.guild.ally")
    @Description("Ta komenda sluzy do zawierania sojuszy")
    @Syntax("<tag>")
    fun onCommand(player: Player, tag: String) {
        val guild = manager.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(config.messages.youDontHaveGuild)
        if (! guild.isLeader(player.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        val ally = manager.getGuildByTag(tag)
                ?: return player.sendMessage(config.messages.cantFindGuildWithTag.rep("%tag%", tag))
        if (guild == ally) return player.sendMessage(config.messages.youCantAllyYourGuild)
        if (manager.hasAlly(guild, ally)) return player.sendMessage(config.messages.yourGuildHasAlreadyAlly.rep("%tag%", ally.tag))
        if (! manager.hasAllyInvite(ally, guild)) {
            if (manager.hasAllyInvite(guild, ally)) return player.sendMessage(config.messages.yourGuildAlreadySendInvite.rep("%tag%", ally.tag))
            manager.createAllyInvite(guild, ally)
            manager.broadcastGuild(guild, config.messages.yourGuildSendInviteAlly.rep("%tag%", ally.tag))
            manager.broadcastGuild(ally, config.messages.guildSendInviteToAlly.rep("%tag%", guild.tag))
            return
        }
        manager.removeAllyInvite(ally, guild)
        manager.createGuildAlly(ally, guild)
        Bukkit.broadcastMessage(config.messages.guildAlliedWithGuild.rep("%tag%", guild.tag).rep("%ally%", ally.tag))
    }
}