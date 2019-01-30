package pl.otekplay.menu.api

import org.bukkit.inventory.InventoryHolder

class MenuHolder(val menu: Menu) : InventoryHolder {
    override fun getInventory() = null

}
