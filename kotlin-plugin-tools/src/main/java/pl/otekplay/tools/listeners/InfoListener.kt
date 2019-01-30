package pl.otekplay.tools.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import pl.otekplay.tools.ToolsPlugin

class InfoListener(val plugin: ToolsPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        e.joinMessage = null
    }


    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        e.quitMessage = null
    }


    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        e.deathMessage = null
    }
}