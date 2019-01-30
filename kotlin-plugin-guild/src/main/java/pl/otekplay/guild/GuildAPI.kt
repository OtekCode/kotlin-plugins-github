package pl.otekplay.guild

import org.bukkit.Location
import pl.otekplay.guild.holders.Guild
import pl.otekplay.guild.holders.GuildManager
import java.util.*

object GuildAPI {
    internal lateinit var plugin:GuildPlugin
    internal val manager: GuildManager get() =plugin.manager

    fun getGuilds() = manager.guilds

    fun getGuildById(uniqueId: UUID) = manager.getGuildById(uniqueId)

    fun getGuildByTag(tag: String) = manager.getGuildByTag(tag)

    fun getGuildByName(name: String) = manager.getGuildByName(name)

    fun getGuildByMember(uniqueId: UUID) = manager.getGuildByMember(uniqueId)

    fun getGuildByLocation(location: Location) = manager.getGuildByLocation(location)

    fun getGuildByCords(x: Int, z: Int) = manager.getGuildByCords(x,z)

    fun getGuildByPlace(place:Int) = manager.getGuildByPlace(place)

    fun getGuildConfig() = plugin.config

    fun broadcastGuild(guild: Guild, string: String) = manager.broadcastGuild(guild,string)

    fun isGuildBlockedByExplode(guild: Guild) = manager.isGuildBlockedByExplode(guild)

    fun getGuildPoints(guild: Guild) = manager.getGuildPoints(guild)

    fun getGuildKills(guild: Guild) = manager.getGuildKills(guild)

    fun getGuildDeaths(guild: Guild) = manager.getGuildDeaths(guild)

    fun getGuildAssists(guild: Guild) = manager.getGuildAssists(guild)

    fun getGuildPlace(guild: Guild) = manager.getGuildPlace(guild)

    fun getGuildTopList() = manager.getTopGuildsSortedList()

}