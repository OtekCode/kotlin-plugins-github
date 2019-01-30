package pl.otekplay.protection

import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import pl.otekplay.plugin.util.Dates
import pl.otekplay.tag.PlayerTagEvent

class ProtectionListener(private val plugin: ProtectionPlugin) : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (! e.player.hasPlayedBefore()) plugin.manager.registerProtection(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val player = e.entity as? Player ?: return
        val attacker = if (e.damager is Arrow) {
            val arrow = e.damager as Arrow
            if (arrow.shooter is Player) arrow.shooter as Player else return
        } else if (e.damager is Player) e.damager as Player else return
        if (plugin.manager.isProtected(attacker.uniqueId)) {
            e.isCancelled = true
            return attacker.sendMessage(plugin.config.messages.youHaveProtection.replace("%time%", Dates.formatData(plugin.manager.getProtectionTimeEnd(attacker.uniqueId))))
        }
        if (plugin.manager.isProtected(player.uniqueId)) {
            e.isCancelled = true
            return attacker.sendMessage(plugin.config.messages.playerHasProtection.replace("%name%", player.name).replace("%time%", Dates.formatData(plugin.manager.getProtectionTimeEnd(player.uniqueId))))
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onTagRefresh(e: PlayerTagEvent) {
        if (plugin.manager.isProtected(e.player.uniqueId)) e.prefix = e.prefix + plugin.config.protectionTag
    }
}