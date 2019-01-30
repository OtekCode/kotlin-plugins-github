package pl.otekplay.worldborder

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent
import pl.otekplay.plugin.util.Locations
import java.util.*
import kotlin.collections.HashMap

class WorldBorderListener(private val plugin: WorldBorderPlugin) : Listener {


    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    fun onPlayerTeleport(e: PlayerTeleportEvent) {
        val player = e.player
        val world = player.world
        val to = e.to
        val border = world.worldBorder
        if (Locations.isInLocation(border.center,border.size.toInt(),to)) return
        e.isCancelled = true
        player.sendMessage(plugin.config.messages.youCantTeleportOutBorder)
    }
}


