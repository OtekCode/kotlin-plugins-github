package pl.otekplay.tools.system.vanish

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import pl.otekplay.tag.PlayerTagEvent

class VanishListener(private val manager: VanishManager) : Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        if (!player.hasPermission(manager.config.permissionVanishBypass)) manager.refreshOthers(player)
        if (!manager.hasVanish(player.uniqueId)) return
        manager.hidePlayer(player)
        player.sendMessage(manager.config.messages.youLoggedWithVanishEnabled)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        if (!manager.hasVanish(player.uniqueId)) return
        manager.showPlayer(player)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerTag(e: PlayerTagEvent) {
        if (!manager.hasVanish(e.player.uniqueId)) return
        e.suffix = e.suffix + manager.config.suffixVanish
    }

}