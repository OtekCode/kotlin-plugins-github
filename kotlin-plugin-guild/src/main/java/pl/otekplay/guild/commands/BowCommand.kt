package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class BowCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("luk|bow")
    @CommandPermission("command.guild.bow")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId) ?: return player.sendMessage(config.messages.youDontHaveGuild)
        if (! guild.isLeader(player.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        val options = guild.options
        if (options.bow) manager.setGuildBow(guild, false) else manager.setGuildBow(guild, true)
        manager.broadcastGuild(guild, if (options.pvp) config.messages.guildBowEnabled else config.messages.guildBowDisabled)
    }
}