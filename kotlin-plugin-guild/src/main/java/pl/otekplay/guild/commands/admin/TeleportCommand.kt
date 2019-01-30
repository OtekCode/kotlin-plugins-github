package pl.otekplay.guild.commands.admin

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.guild.commands.GuildCommand
import pl.otekplay.plugin.rep

@CommandAlias("ga")
@CommandPermission("command.guild.admin")
class TeleportCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("tp")
    @CommandPermission("command.guild.admin.tp")
    @Syntax("<tag>")
    fun onCommand(sender: Player, tag: String) {
        val guild = plugin.manager.getGuildByTag(tag)
                ?: return sender.sendMessage(plugin.config.messages.cantFindGuildWithTag.rep("%tag%", tag))
        sender.teleport(guild.center)
        sender.sendMessage(plugin.config.messages.youTeleportedToGuild.rep("%tag%", guild.tag))
    }


}