package pl.otekplay.trade

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.rep
import java.util.*

class Trade(private val plugin: TradePlugin, offerPlayer: Player, acceptPlayer: Player) {
    private val holder = TradeHolder(this)
    private val offerStock = Stock(offerPlayer.uniqueId, Bukkit.createInventory(holder, plugin.config.tradeInventoryRows * 9, plugin.config.tradeInventoryTitle.rep("%name%", acceptPlayer.name)))
    private val acceptStock = Stock(acceptPlayer.uniqueId, Bukkit.createInventory(holder, plugin.config.tradeInventoryRows * 9, plugin.config.tradeInventoryTitle.rep("%name%", offerPlayer.name)))
    private var end = false
    private var closed = false

    init {
        buildBorder()
        refreshButtonItems()
        offerPlayer.openInventory(offerStock.inventory)
        acceptPlayer.openInventory(acceptStock.inventory)
    }

    private fun buildBorder() {
        val item = plugin.config.borderItem.toItemStack()
        plugin.config.borderItemsSlots.forEach {
            offerStock.inventory.setItem(it, item)
            acceptStock.inventory.setItem(it, item)
        }
    }

    private fun getStockPlayer(uniqueId: UUID) = if (offerStock.uniqueId == uniqueId) offerStock else acceptStock
    private fun getStockEnemy(uniqueId: UUID) = if (offerStock.uniqueId != uniqueId) offerStock else acceptStock

    fun clickMenu(player: Player, slot: Int) {
        if (end) return
        if (plugin.config.buttonItemSlot == slot) {
            val stock = getStockPlayer(player.uniqueId)
            if (isBothReady()) buildBorder()
            stock.ready = ! stock.ready
            refreshButtonItems()
            if (isBothReady())
                checkLater(plugin.config.borderItemsSlots.iterator())
            return
        }

        if (plugin.config.offerItemsSlots.contains(slot)) {
            val stock = getStockPlayer(player.uniqueId)
            if (stock.ready) return
            val inventory = stock.inventory
            val item = inventory.getItem(slot) ?: return
            inventory.setItem(slot, null)
            stock.offered.remove(item)
            Items.addItem(player, item)
            refreshShowItems(stock, stock.offered.iterator())
            val enemy = getStockEnemy(player.uniqueId)
            refreshEnemyItems(enemy, stock.offered.iterator())
        }
    }

    fun clickInventory(player: Player, slot: Int) {
        val inventory = player.inventory
        val item = inventory.getItem(slot) ?: return
        val stock = getStockPlayer(player.uniqueId)
        stock.offered.add(item)
        inventory.setItem(slot, null)
        refreshShowItems(stock, stock.offered.iterator())
        refreshEnemyItems(getStockEnemy(player.uniqueId), stock.offered.iterator())
    }

    fun isBothReady() = offerStock.ready && acceptStock.ready

    private fun checkLater(iterator: Iterator<Int>) {
        plugin.taskLaterSync(object : BukkitRunnable() {
            override fun run() {
                if (closed) return
                if (! isBothReady()) return
                if (! iterator.hasNext()) return endTrade()
                val next = iterator.next()
                val item = plugin.config.loadingItem.toItemStack()
                offerStock.inventory.setItem(next, item)
                acceptStock.inventory.setItem(next, item)
                checkLater(iterator)
            }
        }, plugin.config.tradeCheckTicks)
    }

    private fun endTrade() {
        closed = true
        end = true
        val offerPlayer = Bukkit.getPlayer(offerStock.uniqueId)
        val acceptPlayer = Bukkit.getPlayer(acceptStock.uniqueId)
        Items.addItems(offerPlayer, acceptStock.offered)
        acceptStock.clear()
        acceptPlayer.closeInventory()
        acceptPlayer.sendMessage(plugin.config.messages.tradeHasBeenComplete)
        Items.addItems(acceptPlayer, offerStock.offered)
        offerStock.clear()
        offerPlayer.closeInventory()
        offerPlayer.sendMessage(plugin.config.messages.tradeHasBeenComplete)
    }

    fun closeTrade() {
        if (closed) return
        closed = true
        closeStock(offerStock)
        closeStock(acceptStock)
    }


    private fun closeStock(stock: Stock) {
        val player = Bukkit.getPlayer(stock.uniqueId) ?: return
        stock.apply {
            Items.addItems(player, offered)
            offered.clear()
            inventory.clear()
        }
        player.closeInventory()
        player.sendMessage(plugin.config.messages.tradeHasBeenCancelled)
    }

    private fun refreshButtonItems() {
        refreshReadyItem(offerStock, acceptStock.ready)
        refreshReadyItem(acceptStock, offerStock.ready)
    }

    private fun refreshReadyItem(stock: Stock, enemyReady: Boolean) {
        val buttonItem = if (stock.ready) plugin.config.cancelItem.toItemStack() else plugin.config.readyItem.toItemStack()
        val infoItem = if (stock.ready) plugin.config.acceptedInfoItem.toItemStack() else plugin.config.cancelInfoItem.toItemStack()
        val enemyItem = if (enemyReady) plugin.config.acceptedInfoItem.toItemStack() else plugin.config.cancelInfoItem.toItemStack()
        stock.inventory.apply {
            setItem(plugin.config.buttonItemSlot, buttonItem)
            setItem(plugin.config.readyInfoItemSlot, infoItem)
            setItem(plugin.config.enemyReadyInfoItemSlot, enemyItem)
        }

    }

    private fun refreshEnemyItems(stock: Stock, iterator: Iterator<ItemStack>) = stock.setItems(plugin.config.showItemsSlots, iterator)

    private fun refreshShowItems(stock: Stock, iterator: Iterator<ItemStack>) = stock.setItems(plugin.config.offerItemsSlots, iterator)


}
