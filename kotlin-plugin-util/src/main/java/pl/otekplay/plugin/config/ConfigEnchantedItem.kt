package pl.otekplay.plugin.config

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import pl.otekplay.plugin.builders.ItemBuilder
import pl.otekplay.plugin.bukkitName


open class ConfigEnchantedItem(
        val name: String,
        val id: Int,
        private val amount: Int = 1,
        private val data: Short = 0,
        private val lore: List<String> = arrayListOf(),
        private val enchants: HashMap<Int, Int> = HashMap()
) {
    companion object {
        fun buildEnchantedItemOrNull(item: ItemStack) = if(item.type == Material.AIR) null else ConfigEnchantedItem(item)
    }
 constructor(item: ItemStack):this(
            if(item.hasItemMeta() && item.itemMeta.hasDisplayName()) item.itemMeta.displayName else item.type.bukkitName(),
            item.type.id,
            item.amount,
            item.durability,
            if(item.hasItemMeta() && item.itemMeta.hasLore()) item.itemMeta.lore else arrayListOf(),
            item.enchantments.map { Pair(it.key.id,it.value) }.toMap(HashMap())
    )
    fun toItemStack() = ItemBuilder(Material.getMaterial(id), amount, data, name, lore, enchants.mapKeys { Enchantment.getById(it.key) }).buildItemStack()
}