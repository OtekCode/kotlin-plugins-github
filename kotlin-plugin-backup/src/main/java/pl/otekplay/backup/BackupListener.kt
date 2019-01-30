package pl.otekplay.backup

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent

class BackupListener(private val plugin: BackupPlugin):Listener {

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        plugin.manager.saveBackup(e.entity, BackupType.DEATH)
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val uniqueId = e.player.uniqueId
        if (plugin.manager.getBackup(uniqueId) != null) return
        plugin.manager.registerBackup(uniqueId)
    }
}