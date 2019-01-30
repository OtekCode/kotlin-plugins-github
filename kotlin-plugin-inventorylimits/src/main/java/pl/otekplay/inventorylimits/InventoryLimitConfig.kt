package pl.otekplay.inventorylimits

import org.bukkit.Material
import pl.otekplay.inventorylimits.deposit.ConfigDeposit
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions

class InventoryLimitConfig : PluginConfiguration {
    val menuOptions = ConfigMenuOptions("Schowek", 5, ConfigMenuFill(false, ConfigItem("Example", Material.GLASS.id)))
    val limits = listOf(
            ConfigDeposit(Material.GOLDEN_APPLE.id, 1, 4, ConfigMenuItem(0, ConfigItem("Zlote Enchantowe Jabluszka!", Material.GOLDEN_APPLE.id, 1, 1, listOf("Depozyt: %deposit%","Limit: %limit%","Posiadasz: %amount%"))), "Zabralo Ci %consume% zlotych enchantowych jabluszek, limit to %limit%"),
            ConfigDeposit(Material.GOLDEN_APPLE.id, 0, 8, ConfigMenuItem(1, ConfigItem("Zlote Jabluszka!", Material.GOLDEN_APPLE.id, 1)), "Zabralo Ci %consume% zlotych jabluszek, limit to %limit%")
    )
   val messages = InventoryLimitMessages()
}
