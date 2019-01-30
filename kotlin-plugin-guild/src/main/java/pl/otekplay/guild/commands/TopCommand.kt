package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class TopCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("top")
    @CommandPermission("command.guild.top")
    fun onCommand(player: Player) {
       plugin.manager.openTopGuildMenu(player)
    }
}