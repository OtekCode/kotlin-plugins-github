package pl.otekplay.crafting

import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions

data class ConfigCraft(
        val menuOptions: ConfigMenuOptions,
        val optionItem: ConfigMenuItem,
        val infoItems: ArrayList<ConfigMenuItem>
)