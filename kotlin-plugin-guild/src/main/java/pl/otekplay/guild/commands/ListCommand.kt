package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class ListCommand(plugin: GuildPlugin) : GuildCommand(plugin){

    @Subcommand("list")
    @CommandPermission("command.guild.list")
    fun onCommand(sender: CommandSender){
        sender.sendMessage(config.messages.guildListCommandHeader)
        plugin.manager.guilds.forEach{
            sender.sendMessage(config.messages.guildListCommandFormat
                    .rep("%tag%",it.tag)
                    .rep("%name%",it.name)
                    .rep("%leader%", Bukkit.getOfflinePlayer(it.leaderId).name)
            )
        }
    }
}