package pl.otekplay.tag

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class TagListener(private val plugin: TagPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        plugin.manager.createPlayer(player)
        plugin.manager.updatePlayer(player)

    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        plugin.manager.removeBoard(player)
    }
}