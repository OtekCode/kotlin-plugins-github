package pl.otekplay.guild.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockPistonExtendEvent
import pl.otekplay.guild.GuildPlugin

class PistonListeners(plugin: GuildPlugin) : GuildListener(plugin) {

    @EventHandler
    fun onBlockPistonExtend(e: BlockPistonExtendEvent) {
        val bf = e.direction
        val block = e.block
        if (manager.getGuildByLocation(block.location) == null) return
        for (i in 1..15) {
            if (block.getRelative(bf, i).type != Material.DRAGON_EGG) continue
            e.isCancelled = true
            break
        }
    }

}