package pl.otekplay.guild.commands.admin

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.guild.commands.GuildCommand

@CommandAlias("ga")
@CommandPermission("command.guild.admin")
class ReloadCommand(plugin: GuildPlugin) : GuildCommand(plugin) {


    @Subcommand("reload")
    @CommandPermission("command.guild.admin.reload")
    fun onCommand(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedGuildConfig)
    }
}