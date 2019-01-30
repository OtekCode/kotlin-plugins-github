package pl.otekplay.market

import org.bukkit.entity.Player
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick

class MarketMenu(val plugin: MarketPlugin) {

    private val backButtonEvent = object : ItemClick {
        override fun click(player: Player) {
            openMarketMenu(player)
        }
    }
    private val backButtonItem = Item(plugin.config.itemBack.toItemStack(), backButtonEvent)

    fun addBackButtonToMenu(menu: Menu) {
        menu.setAsLast(backButtonItem)
    }


    fun openMarketMenu(player: Player) {
        val menu = Menu(plugin.config.optionsMarketMenu)
        plugin.config.itemAdd.run {
            menu.setItem(slot, Item(item.toItemStack(), object : ItemClick {
                override fun click(player: Player) {
                    plugin.ownerMenu.openMenuItemAdd(player)
                }
            }))
        }
        plugin.config.itemRemove.run {
            menu.setItem(slot, Item(item.toItemStack(), object : ItemClick {
                override fun click(player: Player) {
                    plugin.ownerMenu.openMenuItemRemove(player)
                }
            }))
        }
        plugin.config.itemManage.run {
            menu.setItem(slot, Item(item.toItemStack(), object : ItemClick {
                override fun click(player: Player) {
                    plugin.ownerMenu.openMenuItemManage(player)
                }
            }))
        }
        menu.open(player)
    }


}