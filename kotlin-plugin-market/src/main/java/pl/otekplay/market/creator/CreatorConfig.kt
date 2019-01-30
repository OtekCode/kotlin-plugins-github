package pl.otekplay.market.creator

import pl.otekplay.market.creator.CreatorItem
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuOptions

 class CreatorConfig(
         val options: ConfigMenuOptions,
         val icon: ConfigEnchantedItem,
         val items: ArrayList<CreatorItem>
) :PluginConfiguration