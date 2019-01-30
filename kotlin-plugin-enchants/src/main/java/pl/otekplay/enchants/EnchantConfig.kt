package pl.otekplay.enchants

import org.bukkit.enchantments.Enchantment
import pl.otekplay.plugin.api.PluginConfiguration

class EnchantConfig:PluginConfiguration{
    val enchants: HashMap<Int,Int> = hashMapOf(
            Pair(Enchantment.PROTECTION_ENVIRONMENTAL.id,3),
            Pair(Enchantment.DAMAGE_ALL.id,3)
    )
    val randomSeedPerPrepare = true
    val clearEnchantNames = true
    val autoLapisEnchant = true
    val messages = EnchantMessages()
}