package pl.otekplay.tab

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class TabUpdate(private val plugin: TabPlugin) : BukkitRunnable() {
    init {
        plugin.logger.info("Running tablist update every ${plugin.config.updateInterval} milis.")
    }
    override fun run() {
        plugin.logger.info("Updating tablist...")
        Bukkit.getOnlinePlayers().forEach {
            plugin.manager.update(it)
        }
    }
}