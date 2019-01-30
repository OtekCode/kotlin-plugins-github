package pl.otekplay.ranking

import org.bukkit.Material
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class ConfigTopMenu {
    val options = ConfigMenuOptions("Ranking Topek",6, ConfigMenuFill(false, ConfigItem("elo", Material.DIAMOND.id)))
    val sizeTop = 9
    val itemName = "Gracz %name%"
    val itemLore = listOf(
            "Miejsce: %place%",
            "Punkty: %points%",
            "Zabojstwa: %kills%",
            "Zgony: %deaths%",
            "Assysty: %assists%"
    )
}