package pl.otekplay.itemshop

import pl.otekplay.plugin.api.PluginConfiguration

class ItemShopConfig:PluginConfiguration {
    val hostname = "localhost"
    val port = 25009
    val messages = ItemShopMessages()
}