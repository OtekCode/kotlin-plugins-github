package pl.otekplay.fight

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

import pl.otekplay.plugin.rep
import pl.otekplay.plugin.sendActionBar

class FightTask(val plugin: FightPlugin) : BukkitRunnable() {
    override fun run() {
        plugin.manager.validFights.forEach {
            val time = (it.lastAttack + plugin.config.validAttackerTime) - System.currentTimeMillis()
            Bukkit.getPlayer(it.uniqueId).sendActionBar(plugin.config.messages.antyRelog.rep("%time%", ((time + 1000) / 1000).toString()))
        }
    }
}