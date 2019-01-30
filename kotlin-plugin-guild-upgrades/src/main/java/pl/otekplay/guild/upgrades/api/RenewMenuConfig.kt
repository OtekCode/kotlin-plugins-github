package pl.otekplay.guild.upgrades.api

import org.bukkit.Material
import pl.otekplay.menu.api.MenuConfig
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.*
import java.util.concurrent.TimeUnit

class RenewMenuConfig : PluginConfiguration {
    val enabled = true
    val optionItem = ConfigMenuItem(15, ConfigItem("Przedluzanie!", Material.DIAMOND.id, 20))
    val menuOptions = ConfigMenuOptions("Przedluzanie", 1, ConfigMenuFill(true, MenuConfig.fillerItem))
    val renews = arrayListOf(
            RenewConfig(TimeUnit.DAYS.toMillis(1), ConfigBuyMenuItem(
                    ConfigMenuItem(0, ConfigItem("Przedluz o 1 Dzien", Material.STONE.id)),
                    arrayListOf()
            )),
            RenewConfig(TimeUnit.DAYS.toMillis(2), ConfigBuyMenuItem(
                    ConfigMenuItem(1, ConfigItem("Przedluz o 2 Dzi", Material.STONE.id)),
                    arrayListOf()
            )),
            RenewConfig(TimeUnit.DAYS.toMillis(3), ConfigBuyMenuItem(
                    ConfigMenuItem(2, ConfigItem("Przedluz o 3 Dzi", Material.STONE.id)),
                    arrayListOf()
            ))

    )

}