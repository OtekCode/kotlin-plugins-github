package pl.otekplay.tools.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import pl.otekplay.tools.ToolsPlugin

class InventoryListener(val plugin: ToolsPlugin) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked as? Player ?: return
        if (e.clickedInventory?.holder == player) return
        if (e.clickedInventory?.type == InventoryType.ENDER_CHEST && player.hasPermission(plugin.config.permissionManipulateEnderchestPlayer)) {
            e.isCancelled = true
            return
        }
        if (e.clickedInventory?.type == InventoryType.PLAYER && ! player.hasPermission(plugin.config.permissionManipulateInventoryPlayer)) {
            e.isCancelled = true
            return
        }

    }
}