package pl.otekplay.tab

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class TabListener(private val plugin: TabPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        plugin.taskAsync(object : BukkitRunnable() {
            override fun run() {
                plugin.manager.create(e.player)
                plugin.manager.update(e.player)
            }
        })
    }
}