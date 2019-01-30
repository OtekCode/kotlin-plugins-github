package pl.otekplay.home

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class HomeListener(private val plugin: HomePlugin) : Listener {


    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        if (plugin.manager.getHome(player.uniqueId) == null) plugin.manager.createHome(e.player.uniqueId)
    }
}