package pl.otekplay.guild.upgrades.api

import pl.otekplay.plugin.config.ConfigBuyMenuItem

data class UpgradeConfig(
        val value: Int,
        val buyMenuItem: ConfigBuyMenuItem
)