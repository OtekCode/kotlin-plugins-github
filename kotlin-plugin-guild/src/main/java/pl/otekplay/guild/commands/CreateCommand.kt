package pl.otekplay.guild.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Locations
import pl.otekplay.spawn.SpawnAPI

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class CreateCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("zaloz|stworz|create")
    @Syntax("<tag> <nazwa>")
    @CommandPermission("command.guild.create")
    fun onCommand(player: Player, tag: String,@Single name: String) {
        if (manager.getGuildByMember(player.uniqueId) != null) return player.sendMessage(config.messages.youAlreadyGotGuild)
        if (tag.length != 4) return player.sendMessage(config.messages.guildTagIsInvalid)
        if (manager.getGuildByTag(tag) != null) return player.sendMessage(config.messages.guildTagIsReserved.rep("%tag%", tag))
        if (manager.getGuildByName(name) != null) return player.sendMessage(config.messages.guildNameIsReserved.rep("%name%", tag))
        val location = player.location
        if(plugin.config.guildCreateItems.any {!it.available }) return player.sendMessage(config.messages.allItemsMustBeDiscovered)
        if (Locations.isInLocation(SpawnAPI.spawnLocation, SpawnAPI.cuboidRadiusCuboid + config.guildDistanceFromBorderSpawn, location)) return player.sendMessage(config.messages.guildIsTooCloseSpawn)
        if(Dates.parseData(plugin.config.guildCreateAvailableTime).time > System.currentTimeMillis()) return player.sendMessage(plugin.config.messages.guildCreateIsNotAvailable.rep("%date%",plugin.config.guildCreateAvailableTime))
        if (manager.getGuildsNear(location.blockX, location.blockZ, config.guildCuboidDistanceBetweenGuilds).isNotEmpty()) return player.sendMessage(config.messages.guildIsTooCloseGuild)
        if (! manager.checkCreateGuildItems(player)) return player.sendMessage(config.messages.youDontHaveGuildItems)
        manager.removeCreateGuildItems(player)
        manager.createGuild(tag, name, player.uniqueId, location, config.guildStartLives, config.guildCuboidStartSize, config.guildMembersStartSize)
        Bukkit.broadcastMessage(config.messages.guildHasBeenCreated
                .rep("%tag%", tag)
                .rep("%name%", name)
                .rep("%name%", player.name)
        )
    }


}