package pl.otekplay.market.owner

import org.bukkit.entity.Player
import pl.otekplay.market.MarketPlugin
import pl.otekplay.market.offer.PrepareOffer
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.util.Items

class OwnerMenu(val plugin: MarketPlugin) {

    fun openMenuItemAdd(player: Player) {
        val menu = Menu(plugin.ownerConfig.optionsMenuItemAdd)
        val owner = plugin.ownerManager.getOwner(player.uniqueId) ?: return
        val inventory = player.inventory
        inventory.contents.forEachIndexed { i, item ->
            if (item == null) return@forEachIndexed
            val menuItem = Item(item, object : ItemClick {
                override fun click(player: Player) {
                    val clickedItem = inventory.getItem(i)
                    inventory.setItem(i, null)
                    owner.items.add(ConfigEnchantedItem(clickedItem))
                    owner.updateEntity()
                    player.sendMessage(plugin.ownerConfig.messages.youAddedItemToList)
                    openMenuItemAdd(player)
                }
            })
            menu.setItem(i, menuItem)
        }
        plugin.marketMenu.addBackButtonToMenu(menu)
        menu.open(player)
    }

    fun openMenuItemManage(player: Player) {
        val menu = Menu(plugin.ownerConfig.optionsMenuItemManage)
        val owner = plugin.ownerManager.getOwner(player.uniqueId) ?: return
        owner.items.forEach {
            val stack = it.toItemStack()
            menu.addItem(Item(stack, object : ItemClick {
                override fun click(player: Player) {
                    val prepare = PrepareOffer(it)
                    plugin.offerMenu.openPrepareOfferMenu(player, prepare)
                }

            }))
        }
        plugin.marketMenu.addBackButtonToMenu(menu)
        menu.open(player)
    }

    fun openMenuItemRemove(player: Player) {
        val menu = Menu(plugin.ownerConfig.optionsMenuItemRemove)
        val owner = plugin.ownerManager.getOwner(player.uniqueId) ?: return
        owner.items.forEach {
            val stack = it.toItemStack()
            menu.addItem(Item(stack, object : ItemClick {
                override fun click(player: Player) {
                    owner.items.remove(it)
                    owner.updateEntity()
                    Items.addItem(player, stack)
                    player.sendMessage(plugin.ownerConfig.messages.youRemovedItemFromList)
                    openMenuItemRemove(player)
                }

            }))
        }
        plugin.marketMenu.addBackButtonToMenu(menu)
        menu.open(player)
    }
}