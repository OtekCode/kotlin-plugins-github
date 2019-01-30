package pl.otekplay.trade

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

data class Stock(
        val uniqueId: UUID,
        val inventory: Inventory
) {
    val offered = ArrayList<ItemStack>()
    var ready = false

    fun setItems(slots: ArrayList<Int>, items: Iterator<ItemStack>) {
        slots.forEach { inventory.setItem(it, null) }
        slots.forEach { if (items.hasNext()) inventory.setItem(it, items.next()) }
    }

    fun clear(){
        inventory.clear()
        offered.clear()
    }

}