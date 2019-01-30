package pl.otekplay.kit

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class KitListener(private val plugin: KitPlugin):Listener {


    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (plugin.manager.getUser(e.player.uniqueId) == null) plugin.manager.createUser(e.player.uniqueId)
    }
}