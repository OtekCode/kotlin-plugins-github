package pl.otekplay.drop.config.bonus

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class Fortune(
        enabled: Boolean,
        private val level: Int,
        chance: Double,
        dropExp: Int,
        playerExp: Int,
        amount: Int
) : Bonus(
        enabled,
        chance,
        dropExp,
        playerExp,
        amount
) {
    fun checkValidPickAxe(item: ItemStack?): Boolean {
        if (!enabled) return false
        return getLevelItem(item) == level
    }

    private fun getLevelItem(item: ItemStack?): Int {
        if (item == null || !item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return 0
        return item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)
    }
}

