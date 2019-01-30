package pl.otekplay.plugin.config

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import pl.otekplay.plugin.builders.ItemBuilder
import pl.otekplay.plugin.bukkitName

open class ConfigItem(
        val name: String,
        private val id: Int,
        private val amount: Int = 1,
        private val data: Short = 0,
        private val lore: List<String> = arrayListOf()
) {
    companion object {
        val DEFAULT = ConfigItem("Default",Material.STONE.id)
    }
    constructor(item:ItemStack):this(
            if(item.hasItemMeta() && item.itemMeta.hasDisplayName()) item.itemMeta.displayName else item.type.bukkitName(),
            item.type.id,
            item.amount,
            item.durability,
            if(item.hasItemMeta() && item.itemMeta.hasLore()) item.itemMeta.lore else arrayListOf()
    )
    fun toItemStack() = ItemBuilder(Material.getMaterial(id), amount, data, name, lore).buildItemStack()
}