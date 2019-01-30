package pl.otekplay.drop.config

import org.bukkit.inventory.ItemStack

class DropItem(
        val enabled:Boolean,
        val id: Int,
        private val data: Int
) {

    fun generateItem(amount: Int) = if(!enabled) null else ItemStack(id,  amount, data.toShort())
    

}