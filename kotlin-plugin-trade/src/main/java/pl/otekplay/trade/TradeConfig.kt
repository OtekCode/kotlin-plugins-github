package pl.otekplay.trade

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem

class TradeConfig : PluginConfiguration {
    val messages = TradeMessages()
    val showItemsSlots = arrayListOf(5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44, 50, 51, 52)
    val offerItemsSlots = arrayListOf(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 38, 39, 46, 47)
    val borderItemsSlots = arrayListOf(4, 13, 22, 31, 40, 49)
    val buttonItemSlot = 45
    val readyInfoItemSlot = 48
    val enemyReadyInfoItemSlot = 53
    val borderItem = ConfigItem("Border", Material.EMERALD.id)
    val readyItem = ConfigItem("Akceptuj", Material.WOOL.id, 1, 6)
    val cancelItem = ConfigItem("Anuluj", Material.WOOL.id, 1, 14)
    val acceptedInfoItem = ConfigItem("Gotowy!", Material.WOOL.id, 1, 6)
    val cancelInfoItem = ConfigItem("Nie Gotowy", Material.WOOL.id, 1, 14)
    val loadingItem = ConfigItem("Ladowanie", Material.DIAMOND_BLOCK.id)
    val tradeCheckTicks = 10L
    val inviteTradeTime = 30000L
    val tradeInventoryRows = 6
    val tradeInventoryTitle = "Wymiana"
    val tradeMaxDistanceBeetwenPlayers = 5
}