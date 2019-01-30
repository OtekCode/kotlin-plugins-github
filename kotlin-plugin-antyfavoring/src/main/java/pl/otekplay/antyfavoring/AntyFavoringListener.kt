package pl.otekplay.antyfavoring

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCreativeEvent
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates

class AntyFavoringListener(private val plugin: AntyFavoringPlugin) : Listener {

//    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
//    fun onBlockPlace(e: BlockPlaceEvent) {
//        if (e.player.hasPermission(plugin.config.permissionCreativeBlockBuild)) return
//        e.player.sendMessage(plugin.config.messageCantBuildWhileStaff)
//        e.isCancelled = true
//    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onInventoryCreative(e: InventoryCreativeEvent) {
        val player = e.whoClicked as? Player ?: return
        if (! player.hasPermission(plugin.config.permissionCreativeAccess)) {
            e.isCancelled = true
            return
        }
        val item = e.cursor ?: return
        val meta = item.itemMeta ?: return
        meta.lore = plugin.config.messages.creativeItemLore.map {
            it
                    .replace("%author%", player.name)
                    .replace("%date%", Dates.formatData(System.currentTimeMillis()))
                    .replace("%amount%", item.amount.toString())
        }
        item.itemMeta = meta
        e.cursor = item
        val message = plugin.config.messages.creativeClickItem.rep("%name%", player.name).rep("%material%", item.type.name).rep("%amount%", item.amount.toString())
        Bukkit.getOnlinePlayers()
                .filter { it.hasPermission(plugin.config.permissionCreativeClickItemBroadcast) }
                .forEach { it.sendMessage(message) }
    }
}