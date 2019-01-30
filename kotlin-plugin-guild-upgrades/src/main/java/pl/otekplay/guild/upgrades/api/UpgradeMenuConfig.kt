package pl.otekplay.guild.upgrades.api

import org.bukkit.Material
import pl.otekplay.menu.api.MenuConfig
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.*

open class UpgradeMenuConfig : PluginConfiguration {

    val enabled = true
    val optionItem = ConfigMenuItem(15, ConfigItem("UpgradeConfig!", Material.DIAMOND.id, 20))
    val menuOptions = ConfigMenuOptions("Ulepszenia",6, ConfigMenuFill(true,MenuConfig.fillerItem))
    val upgradeHasSlotItem = 9
    val upgrades = arrayListOf(
            UpgradeConfig(20, ConfigBuyMenuItem(
                    ConfigMenuItem(1,
                            ConfigItem("Ulepszenie 1!", Material.DIAMOND.id)
                    ),
                    arrayListOf(
                            ConfigItem("g1", Material.DIAMOND.id),
                            ConfigItem("g2", Material.DIAMOND.id, 2),
                            ConfigItem("g3", Material.DIAMOND.id, 3)
                    ))),
            UpgradeConfig(30, ConfigBuyMenuItem(
                    ConfigMenuItem(3,
                            ConfigItem("Ulepszenie 2!", Material.DIAMOND.id)
                    ),
                    arrayListOf(
                            ConfigItem("g1", Material.DIAMOND.id),
                            ConfigItem("g2", Material.DIAMOND.id, 2),
                            ConfigItem("g3", Material.DIAMOND.id, 3)
                    ))),
            UpgradeConfig(40, ConfigBuyMenuItem(
                    ConfigMenuItem(1,
                            ConfigItem("Ulepszenie 3!", Material.DIAMOND.id)
                    ),
                    arrayListOf(
                            ConfigItem("g1", Material.DIAMOND.id),
                            ConfigItem("g2", Material.DIAMOND.id, 2),
                            ConfigItem("g3", Material.DIAMOND.id, 3)
                    )))
    )


}
