package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
abstract class GuildCommand(val plugin: GuildPlugin) : PluginCommand() {
    val manager = plugin.manager
    val config = plugin.config

}