package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class ExpireCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("wygasa|expire")
    @CommandPermission("command.guild.expire")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(plugin.config.messages.youDontHaveGuild)
        if (manager.isGuildExpired(guild)) return player.sendMessage(plugin.config.messages.guildHasBeenExpired)
        player.sendMessage(plugin.config.messages.guildTimeToExpire.rep("%time%", Dates.formatData(guild.expireTime)))
    }
}