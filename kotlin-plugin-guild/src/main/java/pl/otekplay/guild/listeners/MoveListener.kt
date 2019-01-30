package pl.otekplay.guild.listeners

import com.google.common.cache.CacheBuilder
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.guild.holders.Guild
import pl.otekplay.plugin.rep
import java.util.*
import java.util.concurrent.TimeUnit

class MoveListener(plugin: GuildPlugin) : GuildListener(plugin) {


    private val cachedTerrains = CacheBuilder
            .newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build<UUID, UUID>()
    private val cachedReminds = CacheBuilder
            .newBuilder()
            .expireAfterWrite(plugin.config.guildRemindAboutEnemiesTime, TimeUnit.MILLISECONDS)
            .build<UUID, UUID>()

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        if (e.from.blockX == e.to.blockX) return
        if (e.from.blockZ == e.to.blockZ) return
        val player = e.player
        val location = player.location
        val guild = manager.getGuildByLocation(location)
        val id = cachedTerrains.getIfPresent(player.uniqueId)
        if (guild == null && id == null) return
        if (guild == null) {
            val idGuild = manager.getGuildById(id !!) ?: return
            cachedTerrains.invalidate(player.uniqueId)
            return player.sendMessage(plugin.config.messages.youMovedOnWildressTerrain.rep("%tag%", idGuild.tag))
        }
        if (id == null) {
            cachedTerrains.put(player.uniqueId, guild.guildId)
            if (guild.getMember(player.uniqueId) != null) return player.sendMessage(plugin.config.messages.youMovedOnYourGuildTerrain.rep("%tag%", guild.tag))
            val playerGuild = manager.getGuildByMember(player.uniqueId)
            if (playerGuild != null && plugin.manager.hasAlly(guild, playerGuild)) return player.sendMessage(plugin.config.messages.youMovedOnAllyGuildTerrain.rep("%tag%", guild.tag))
            remindGuildAboutEnemyOnTerrain(guild)
            return player.sendMessage(plugin.config.messages.youMovedOnEnemyGuildTerrain.rep("%tag%", guild.tag))
        }

    }


    private fun remindGuildAboutEnemyOnTerrain(guild: Guild) {
        val lastRemind = cachedReminds.getIfPresent(guild.guildId)
        if (lastRemind != null) return
        cachedReminds.put(guild.guildId, guild.guildId)
        manager.sendMessageToGuild(guild, plugin.config.messages.enemyIsOnTerrainGuild)
    }


}