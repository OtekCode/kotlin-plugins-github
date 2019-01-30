package pl.otekplay.market.offer

import pl.otekplay.plugin.config.ConfigItem

data class BuyTime(
        val timeItem: Long,
        val payItem: ConfigItem
)