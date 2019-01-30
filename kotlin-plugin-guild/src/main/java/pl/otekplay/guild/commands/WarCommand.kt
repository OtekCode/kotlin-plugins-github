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
@CommandPermission("command.guild")class WarCommand(plugin: GuildPlugin) : GuildCommand(plugin) {
    @Subcommand("wojna|war")
    @CommandPermission("command.guild.war")
    @Syntax("<tag>")
    fun onCommand(player: Player, tag: String) {
        val guild = manager.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(config.messages.youDontHaveGuild)
        if (! guild.isLeader(player.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        val war = manager.getGuildByTag(tag)
                ?: return player.sendMessage(config.messages.cantFindGuildWithTag.rep("%tag%", tag))
        if (! manager.hasAlly(guild, war)) return player.sendMessage(config.messages.guildDontHaveAllyWithYou.rep("%tag%", war.tag))
        manager.removeGuildAlly(guild, war)
        Bukkit.broadcastMessage(config.messages.guildPeelAllyWithGuild.rep("%tag%", guild.tag).rep("%war%", war.tag))
    }
}