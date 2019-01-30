package pl.otekplay.guild.upgrades

import org.bukkit.Material
import pl.otekplay.menu.api.MenuConfig
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class GuildUpgradeConfig:PluginConfiguration {
    val messages = GuildUpgradeMessages()
    val upgradeMenuOptions = ConfigMenuOptions("Gildia",5, ConfigMenuFill(true,MenuConfig.fillerItem))
    val itemsMenuOptions = ConfigMenuOptions("Potrzebujesz",2, ConfigMenuFill(true,MenuConfig.fillerItem))
    val hasItem = ConfigItem("Posiadasz!", Material.STAINED_GLASS_PANE.id, 1, 5)
    val needItem = ConfigItem("Potrzebujesz!", Material.STAINED_GLASS_PANE.id, 1, 14)

}