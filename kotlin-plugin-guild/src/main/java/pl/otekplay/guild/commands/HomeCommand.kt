package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.teleporter.TeleportAPI

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class HomeCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("dom|baza|home")
    @CommandPermission("command.guild.home")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId) ?: return player.sendMessage(plugin.config.messages.youDontHaveGuild)
        TeleportAPI.teleportPlayerToLocationWithDelay(player,guild.home,config.messages.guildHomeDelay,true)
    }
}