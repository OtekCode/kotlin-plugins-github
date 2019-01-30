package pl.otekplay.effectshop

import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigPotionEffect

class ConfigEffect(
        val effect: ConfigPotionEffect,
        val itemMenu: ConfigMenuItem,
        val needItems: ArrayList<ConfigItem>
)
