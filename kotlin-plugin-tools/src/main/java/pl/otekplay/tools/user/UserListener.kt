package pl.otekplay.tools.user

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class UserListener(val manager: UserManager) : Listener {


    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        val user = manager.getUser(player.uniqueId) ?: manager.createUser(player.uniqueId,player.name)
        user.lastJoinTime = System.currentTimeMillis()
        user.updateEntity()
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        val user = manager.getUser(player.uniqueId) ?: manager.createUser(player.uniqueId,player.name)
        user.lastQuitTime = System.currentTimeMillis()
        user.updateEntity()
    }
}