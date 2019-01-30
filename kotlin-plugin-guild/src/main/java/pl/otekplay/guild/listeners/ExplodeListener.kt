package pl.otekplay.guild.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityExplodeEvent
import pl.otekplay.guild.GuildPlugin

class ExplodeListener(plugin: GuildPlugin) : GuildListener(plugin) {


    @EventHandler(priority = EventPriority.HIGH,ignoreCancelled = true)
    fun onEntityExplode(e: EntityExplodeEvent) {
        for (block in e.blockList()) {
            val guild = manager.getGuildByLocation(block.location) ?: continue
            return manager.explodeOnTerrainGuild(guild)
        }

    }
}