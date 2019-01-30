package pl.otekplay.plugin.builders

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import pl.otekplay.plugin.rep

class ItemBuilder(
        private val material: Material,
        val amount: Int = 1,
        val data: Short = 0,
        val name: String = "",
        private val lore: List<String> = arrayListOf(),
        private val map: Map<Enchantment, Int> = HashMap()
) {
    constructor(material: Material, name: String) : this(material, 1, 0, name)
    constructor(material: Material, name: String, lore: List<String>) : this(material, 1, 0, name, lore)
    constructor(material: Material, lore: List<String>) : this(material, 1, 0, "", lore)
    constructor(material: Material, amount: Int, name: String) : this(material, amount, 0, name)
    constructor(material: Material, amount: Int, lore: List<String>) : this(material, amount, 0, "", lore)


    fun buildItemStack(): ItemStack {
        var fixAmount = amount
        if (fixAmount > material.maxStackSize) {
            fixAmount = material.maxStackSize
        } else if (fixAmount <= 0) {
            fixAmount = 1
        }
        val item = ItemStack(material, fixAmount, data)
        map.forEach {
            if (it.value > it.key.maxLevel) item.addUnsafeEnchantment(it.key, it.value) else item.addEnchantment(it.key, it.value)
        }
        val meta = item.itemMeta ?: return item
        if (name.isNotEmpty()) {
            meta.displayName = name
        }
        meta.lore = lore
        item.itemMeta = meta
        return item
    }
}

fun main(args: Array<String>) {
    println(Material.DIAMOND_SWORD.name.toLowerCase().split("_").map { it.capitalize() }.joinToString { it }.rep(",", ""))
}


