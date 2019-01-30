package pl.otekplay.menu.api


import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import pl.otekplay.menu.api.items.Item
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuOptions

class Menu(
         var name: String,
        private val size: MenuSize,
        private val items: Array<Item?> = arrayOfNulls(size.size),
        private val fillItem: ConfigItem? = null
) {
    constructor(cfg: ConfigMenuOptions) : this(cfg.title, MenuSize.fit(cfg.rows * 9),fillItem =  if(cfg.filled.filled) cfg.filled.item else null)

    fun addItem(item: Item) = items.forEachIndexed { i, it -> if (it == null) return setItem(i, item) }

    fun setItem(position: Int, item: Item) {
        items[position] = item
    }

    fun setAsLast(item: Item){
        setItem(items.size-1,item)
    }

    private fun fillEmptySlots(item: Item) = items.indices.filter { items[it] == null }.forEach { items[it] = item }

    fun open(player: Player) {
        val inventory = Bukkit.createInventory(MenuHolder(this), size.size, name)
        if(fillItem != null) fillEmptySlots(Item(fillItem.toItemStack()))
        items.forEachIndexed { index, item -> if (item != null) inventory.setItem(index, item.icon) }
        player.openInventory(inventory)
    }


    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.click != ClickType.LEFT) return
        val slot = event.rawSlot
        if (slot >= 0 && slot < size.size && items[slot] != null) {
            val player = event.whoClicked as Player
            val item = items[slot]
            item?.action?.click(player)
        }
    }
}


