package pl.otekplay.effectshop

import org.bukkit.entity.Player
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Items

class EffectShopMenu(private val plugin: EffectShopPlugin) {

    fun openMenu(player: Player) {
        val menu = Menu(plugin.config.options)
        plugin.config.effects.forEach {
            val items = it.needItems.map { it.toItemStack() }
            menu.setItem(it.itemMenu.slot, Item(it.itemMenu.item.toItemStack(), object : ItemClick {
                override fun click(player: Player) {
                    if (Items.hasItems(player.inventory, items)) {
                        Items.consumeItems(player.inventory, items)
                        player.addPotionEffect(it.effect.toPotionEffect())
                        player.sendMessage(plugin.config.messages.youBoughtEffect)
                        player.closeInventory()
                        return
                    }
                    player.sendMessage(plugin.config.messages.youDontHaveItems)
                    items.forEach { player.sendMessage(plugin.config.messages.itemsListFormat.rep("%amount%", it.amount.toString()).rep("%name%", if (it.hasItemMeta() && it.itemMeta.hasDisplayName()) it.itemMeta.displayName else it.type.name)) }
                }
            }))
        }
        menu.open(player)
    }
}