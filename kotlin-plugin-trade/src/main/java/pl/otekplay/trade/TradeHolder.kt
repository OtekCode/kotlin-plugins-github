package pl.otekplay.trade

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class TradeHolder(val trade: Trade) : InventoryHolder {
    override fun getInventory(): Inventory? {
        return null
    }
}