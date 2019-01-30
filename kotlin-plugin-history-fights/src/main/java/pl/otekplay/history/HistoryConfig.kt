package pl.otekplay.history

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class HistoryConfig : PluginConfiguration {
    val menuOptions = ConfigMenuOptions("Historia %name%", 5, ConfigMenuFill(false, ConfigItem("Kamien", Material.STONE.id)))


    val historyUserDeathItem = ConfigItem("Umarl", Material.WOOD_SWORD.id, lore = arrayListOf(
            "Otrzymal: %received%",
            "Punkty: %minus%",
            "Od: %start%",
            "Do: %end%",
            "Zabojca: %killername%",
            "Assysty: %assists%"
    ))

    val historyUserKillerItem = ConfigItem("Zabojca", Material.DIAMOND_SWORD.id, lore = arrayListOf(
            "Umarl: %death%",
            "Od: %start%",
            "Do: %end%",
            "Zabojca: %killername%",
            "Assysty: %assists%",
            "Zadal: %total%",
            "Procent: %percent%"
    ))

    val historyUserAssistItem = ConfigItem("Assystowal", Material.GOLD_SWORD.id, lore = arrayListOf(
            "Umarl: %death%",
            "Od: %start%",
            "Do: %end%",
            "Zabojca: %killername%",
            "Assysty: %assists%",
            "Zadal: %total%",
            "Procent: %percent%"
    ))

    val historyAttackItem = ConfigItem("Obrazenia",Material.DIAMOND_SWORD.id,lore = arrayListOf(
            "Czas: %time%",
            "Damage: %damage%"
    ))
}