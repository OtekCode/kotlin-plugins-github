package pl.otekplay.crafting

import pl.otekplay.menu.api.MenuConfig
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class CraftingConfig:PluginConfiguration{
    val menuCrafting = ConfigMenuOptions("Title", 5, ConfigMenuFill(true, MenuConfig.fillerItem))
    val messages = CraftingMessages()
}