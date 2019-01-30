package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class BuildCommand(plugin: GuildPlugin) : GuildCommand(plugin) {


    @Subcommand("budowa|build")
    @CommandPermission("command.guild.build")

    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(plugin.config.messages.youDontHaveGuild)
        return if(!manager.isGuildBlockedByExplode(guild))
            player.sendMessage(plugin.config.messages.yourGuildBuildIsNotBlocked)
        else
            player.sendMessage(plugin.config.messages.yourGuildBuildIsBlockedTo.rep("%date%", Dates.formatData(manager.getGuildAvailableBuild(guild))))
    }
}