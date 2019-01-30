package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep
@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class DeleteCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("usun|delete")
    @CommandPermission("command.guild.delete")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(config.messages.youDontHaveGuild)
        val member = guild.getMember(player.uniqueId) ?: return
        if (! guild.isLeader(member.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        Bukkit.broadcastMessage(config.messages.guildHasBeenDeleted.rep("%tag%", guild.tag))
        manager.removeGuild(guild)
    }
}
