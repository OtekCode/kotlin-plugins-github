package pl.otekplay.permissions

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PermissionListener(private val plugin: PermissionsPlugin) : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (PermissionsAPI.getUser(player.uniqueId) == null) {
            plugin.manager.createUser(player.uniqueId)
        }
        plugin.manager.inject(player, CustomPermissibleBase(player))
    }
}