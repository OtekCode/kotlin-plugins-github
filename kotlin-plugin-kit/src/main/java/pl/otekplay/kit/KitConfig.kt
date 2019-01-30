package pl.otekplay.kit

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class KitConfig:PluginConfiguration {
    val messages = KitMessages()
    val menuKitDisabledString = "Nie"
    val menuKitEnabledString = "Tak"
    val menuKitCooldownNoExist = "Brak"
    val menuOptions = ConfigMenuOptions("Kity",3, ConfigMenuFill(false, ConfigItem("Clean", Material.BEACON.id)))
}