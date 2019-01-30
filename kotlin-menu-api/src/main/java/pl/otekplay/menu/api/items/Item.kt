package pl.otekplay.menu.api.items


import org.bukkit.inventory.ItemStack

open class Item(
        val icon: ItemStack,
        val action: ItemClick? = null
)
