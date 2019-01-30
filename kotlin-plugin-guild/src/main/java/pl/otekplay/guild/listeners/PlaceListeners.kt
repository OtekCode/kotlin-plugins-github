package pl.otekplay.guild.listeners

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import pl.otekplay.guild.GuildPlugin

class PlaceListeners(plugin: GuildPlugin) : GuildListener(plugin) {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) {
        if (placeHere(e.player, e.block.location)) return
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBucketEmpty(e: PlayerBucketEmptyEvent) {
        if (placeHere(e.player, e.blockClicked.location)) return

        e.isCancelled = true
    }


    private fun placeHere(player: Player, location: Location): Boolean {
        val guild = manager.getGuildByLocation(location) ?: return true
        if (manager.isGuildExpired(guild)) return true
        val member = guild.getMember(player.uniqueId)
        if (member == null) {
            player.sendMessage(plugin.config.messages.cantPlaceHereGuild)
            return false
        }
        if (manager.isGuildBlockedByExplode(guild)) {
            player.sendMessage(plugin.config.messages.guildBuildIsBlockedByExplode)
            return false
        }
        return true
    }
}