package pl.otekplay.market

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions

class MarketConfig : PluginConfiguration{
    val optionsMarketMenu = ConfigMenuOptions("Market",4, ConfigMenuFill.DEFAULT)
    val itemBack = ConfigItem("Wroc do marketu!", Material.STONE_BUTTON.id)
    val itemAdd = ConfigMenuItem(0, ConfigItem("Dodaj itemy z inventory!", Material.REDSTONE_TORCH_ON.id))
    val itemRemove = ConfigMenuItem(1, ConfigItem("Usun swoje dodane itemy", Material.REDSTONE_TORCH_OFF.id))
    val itemManage = ConfigMenuItem(2, ConfigItem("Stworz oferte z dodanych itemow!", Material.PORTAL.id))
}