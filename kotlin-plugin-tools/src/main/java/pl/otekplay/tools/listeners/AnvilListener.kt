package pl.otekplay.tools.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.AnvilInventory
import pl.otekplay.tools.ToolsPlugin


class AnvilListener(val plugin: ToolsPlugin) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventoryClick(e: InventoryClickEvent) {
        if (!e.isCancelled) {
            val ent = e.whoClicked
            if (ent is Player) {
                val inv = e.inventory
                if (inv is AnvilInventory) {
                    val view = e.view
                    val rawSlot = e.rawSlot
                    if (rawSlot == view.convertSlot(rawSlot)) {
                        if (rawSlot == 2) {
                            val item = e.currentItem
                            if (item != null) {
                                val meta = item.itemMeta
                                if (meta != null) {
                                    if (meta.hasDisplayName()) e.isCancelled = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}