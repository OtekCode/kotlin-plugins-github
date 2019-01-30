package pl.otekplay.market.offer

import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem

class PrepareOffer(
        val item: ConfigEnchantedItem
) {
    var buyTime: BuyTime? = null
    val neededItems = arrayListOf<ConfigItem>()
}