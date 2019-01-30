package pl.otekplay.effectshop

import org.bukkit.Material
import org.bukkit.potion.PotionEffectType
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.*

class EffectShopConfig : PluginConfiguration {
    val options = ConfigMenuOptions("Efekty", 1, ConfigMenuFill(false, ConfigItem("", Material.WOOD_SWORD.id)))
    val effects = listOf(
            ConfigEffect(
                    ConfigPotionEffect(PotionEffectType.SPEED.id, 3, 60),
                    ConfigMenuItem(0, ConfigItem("Amfetamina", Material.SUGAR.id)),
                    arrayListOf(ConfigItem("", Material.GOLD_INGOT.id))
            )
    )
   val messages = EffectShopMessages()

}