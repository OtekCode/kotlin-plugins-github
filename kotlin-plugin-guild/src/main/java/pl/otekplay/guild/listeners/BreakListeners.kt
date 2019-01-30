package pl.otekplay.guild.listeners

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import pl.otekplay.guild.GuildPlugin

class BreakListeners(plugin: GuildPlugin) : GuildListener(plugin) {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) {
        if(breakHere(e.player,e.block.location)){
            return
        }
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBucketFill(e: PlayerBucketFillEvent) {
        if(breakHere(e.player,e.blockClicked.location)){
           return
        }
        e.isCancelled = true
    }

    private fun breakHere(player:Player,location: Location):Boolean{
        val guild = manager.getGuildByLocation(location) ?: return true
        if(manager.isGuildExpired(guild)) return true
        val member = guild.getMember(player.uniqueId)
        if (member == null) {
            player.sendMessage(plugin.config.messages.cantBreakHereGuild)
            return false
        }
        return true
    }
}