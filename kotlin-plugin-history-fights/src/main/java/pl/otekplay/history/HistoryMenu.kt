package pl.otekplay.history

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Items

class HistoryMenu(val plugin: HistoryPlugin) {

    fun openHistoryMenu(player: Player, offlinePlayer: OfflinePlayer) {
        val menu = Menu(plugin.config.menuOptions)
        menu.name = menu.name.rep("%name%",offlinePlayer.name)
        val map = HashMap<Item, Long>()
        map.putAll(getDeathItems(offlinePlayer))
        map.putAll(getKillerItems(offlinePlayer))
        map.putAll(getAssistItems(offlinePlayer))
        val items = map.keys.sortedBy { map[it] }
        items.forEach { menu.addItem(it) }
        menu.open(player)
    }

    private fun getKillerItems(offlinePlayer: OfflinePlayer) = plugin.manager.getKillerHistories(offlinePlayer.uniqueId).map {
        val history = it
        val itemStack = plugin.config.historyUserKillerItem.toItemStack()
        val lore = arrayListOf(*itemStack.itemMeta.lore.toTypedArray())
        lore.replaceAll { history.replaceString(it) }
        lore.replaceAll { history.killer.replaceString(it) }
        val item = Item(Items.setLoreItemstack(itemStack,lore))
        Pair(item, history.endTime)
    }.toMap()

    private fun getDeathItems(offlinePlayer: OfflinePlayer) = plugin.manager.getDeathHistories(offlinePlayer.uniqueId).map {
        val history = it
        val itemStack = plugin.config.historyUserDeathItem.toItemStack()
        val lore = arrayListOf(*itemStack.itemMeta.lore.toTypedArray())
        lore.replaceAll { history.replaceString(it) }
        val item = Item(Items.setLoreItemstack(itemStack,lore))
        Pair(item, history.endTime)
    }.toMap()


    private fun getAssistItems(offlinePlayer: OfflinePlayer) = plugin.manager.getAssistsHistories(offlinePlayer.uniqueId).map {
        val history = it
        val itemStack = plugin.config.historyUserAssistItem.toItemStack()
        val lore = arrayListOf(*itemStack.itemMeta.lore.toTypedArray())
        lore.replaceAll { history.replaceString(it) }
        lore.replaceAll { history.assists[offlinePlayer.uniqueId]?.replaceString(it) }
        val item = Item(Items.setLoreItemstack(itemStack,lore))
        Pair(item, history.endTime)
    }.toMap()


    fun openFightMenu(player: Player,offlinePlayer: OfflinePlayer) {

    }

    fun buildHistoryFightItem(fight: HistoryFight) {


    }
}