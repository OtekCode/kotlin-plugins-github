package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class PvPCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("pvp")
    @CommandPermission("command.guild.pvp")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId) ?: return player.sendMessage(config.messages.youDontHaveGuild)
        if (!guild.isLeader(player.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        val options = guild.options
        if (options.pvp) manager.setGuildPvP(guild, false) else manager.setGuildPvP(guild, true)
        manager.broadcastGuild(guild, if (options.pvp) config.messages.guildPvPEnabled else config.messages.guildPvPDisabled)

    }
}