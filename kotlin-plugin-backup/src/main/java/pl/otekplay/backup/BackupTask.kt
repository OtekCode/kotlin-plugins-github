package pl.otekplay.backup

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class BackupTask(private val plugin: BackupPlugin) : BukkitRunnable() {
    override fun run() {
        val start = System.currentTimeMillis()
        Bukkit.broadcastMessage(plugin.config.messages.broadcastGlobalSave)
        Bukkit.getOnlinePlayers().forEach { plugin.manager.saveBackup(it, BackupType.AUTO_SAVE) }
        plugin.logger.info("Player backups has been saved in ${System.currentTimeMillis()-start} ms.")
    }
}