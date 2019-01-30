package pl.otekplay.menu

import org.bukkit.entity.Player
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.config.ConfigMenuOptions

data class CustomMenu(
        val command: String,
        private val options: ConfigMenuOptions,
        private val items: ArrayList<CustomItem>
) {


    fun open(player: Player) {
        val menu = Menu(options)
        items.forEach {
            menu.setItem(it.item.slot, Item(it.item.item.toItemStack(), object : ItemClick {
                override fun click(player: Player) {
                    if (it.command == "") return
                    player.performCommand(it.command)
                }
            }))
        }
        menu.open(player)
    }
}