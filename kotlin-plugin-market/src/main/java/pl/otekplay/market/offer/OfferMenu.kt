package pl.otekplay.market.offer

import org.bukkit.entity.Player
import pl.otekplay.market.MarketPlugin
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.util.Items

class OfferMenu(val plugin: MarketPlugin) {

    fun openCreatorNeedItemMenu(player: Player,prepareOffer: PrepareOffer){
       // val menu = Menu(plugin.creatorConfig.optionsMenuCreator)
    }

    fun openPrepareOfferMenu(player: Player, prepareOffer: PrepareOffer) {
        plugin.offerConfig.run {
            val menu = Menu(menuOptionsPrepareItem)
            menu.setItem(choosedItemSlot, Item(prepareOffer.item.toItemStack()))
            timeItems.forEach {
                menu.setItem(it.slot, Item(it.item.toItemStack(), object : ItemClick {
                    override fun click(player: Player) {
                        prepareOffer.buyTime = it.buyTime
                        openPrepareOfferMenu(player, prepareOffer)
                    }
                }))
                if (prepareOffer.buyTime != it.buyTime) return@forEach
                menu.setItem(it.slot + 9, Item(choosedTimeItemBelow.toItemStack()))
            }
            val noSelected = chooseNoSelectedItem.toItemStack()
            chooseItemSlots.forEach {
                menu.setItem(it, Item(noSelected, object : ItemClick {
                    override fun click(player: Player) {

                    }
                }))
            }
            if (prepareOffer.buyTime != null && prepareOffer.neededItems.size > 0) {
                menu.setItem(readyOfferItem.slot, Item(readyOfferItem.item.toItemStack(), object : ItemClick {
                    override fun click(player: Player) {
                        val owner = plugin.ownerManager.getOwner(player.uniqueId) ?: return player.closeInventory()
                        owner.items.remove(prepareOffer.item)
                        owner.updateEntity()
                        val items = prepareOffer.neededItems.map { it.toItemStack() }
                        if (!Items.hasItems(player.inventory,items)) {
                            player.closeInventory()
                            player.sendMessage(messages.youDontHaveEnoughtItems)
                            return
                        }
                        Items.consumeItems(player.inventory,items)
                        plugin.offerManager.addOffer(player, prepareOffer)
                        player.closeInventory()
                        player.sendMessage(messages.youAddedOfferToMarket)
                    }

                }))
            }
            menu.open(player)
        }
    }


}