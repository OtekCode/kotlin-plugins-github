package pl.otekplay.drop.config.options

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class PickAxe(
        val check: Boolean,
        val minMax: MinMax
) {

    fun checkValidPickAxe(item: ItemStack?) = if (! check || minMax.min == 0) true else minMax.isBetween(getLevelItem(item))

    private fun getLevelItem(item: ItemStack?) = if (item == null) 0 else
        when (item.type) {
            Material.DIAMOND_PICKAXE -> 5
            Material.GOLD_PICKAXE -> 4
            Material.IRON_PICKAXE -> 3
            Material.STONE_PICKAXE -> 2
            Material.WOOD_PICKAXE -> 1
            else -> 0
        }

    private fun getMaterialFromLevel(int: Int) = when (int) {
        5 -> Material.DIAMOND_PICKAXE
        4 -> Material.GOLD_PICKAXE
        3 -> Material.IRON_PICKAXE
        2 -> Material.STONE_PICKAXE
        1 -> Material.WOOD_PICKAXE
        else -> Material.AIR
    }

    fun getMinimalMaterialToDrop() = getMaterialFromLevel(minMax.min)
}