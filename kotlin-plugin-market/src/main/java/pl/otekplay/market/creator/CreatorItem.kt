package pl.otekplay.market.creator

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import pl.otekplay.plugin.bukkitName
import pl.otekplay.plugin.config.ConfigEnchantedItem

class CreatorItem(
        val type: CreatorType,
        val slot: Int,
        material: Material,
        name: String = material.bukkitName(),
        data: Short = 0,
        enchants: HashMap<Enchantment, Int> = hashMapOf()
) : ConfigEnchantedItem(
        name,
        material.id,
        1, data,
        arrayListOf(),
        enchants.map { Pair(it.key.id, it.value) }.toMap(HashMap())
)
