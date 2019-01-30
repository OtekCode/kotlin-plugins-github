package pl.otekplay.drop.config

import org.bukkit.inventory.ItemStack

data class DropResult(
        val name: String,
        val dropExp: Int,
        val playerExp: Int,
        val itemStack: ItemStack?
)