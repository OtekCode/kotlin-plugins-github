package pl.otekplay.plugin.util

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*



object Items {

    fun addItems(player: Player, items: Collection<ItemStack>) {
        items.forEach {
            addItem(player, it)
        }
    }

    fun addItems(player: Player, items: Array<ItemStack>) {
        items.forEach {
            addItem(player, it)
        }
    }

    fun addItem(player: Player, item: ItemStack) {
        player.inventory.addItem(item).values.forEach { dropItem(player.location,it) }
    }


    private fun dropItems(location: Location, items: Collection<ItemStack>) {
        items.forEach { location.world.dropItem(location, it) }
    }

    private fun dropItems(location: Location, items: Array<ItemStack>) {
        items.forEach {
            dropItems(location, items)
        }
    }

    fun dropItems(player: Player, items: Collection<ItemStack>) {
        dropItems(player.location, items)
    }

    fun dropItems(player: Player, items: Array<ItemStack>) {
        dropItems(player.location, items)
    }

    fun dropItem(loc: Location, item: ItemStack) {
        loc.world.dropItem(loc, item)
    }

    private fun dropItem(player: Player, item: ItemStack) {
        player.world.dropItem(player.location, item)
    }

    fun hasItem(inventory: Inventory, item: ItemStack) = countItem(inventory, item) >= item.amount

    fun hasItems(inventory: Inventory, items: Collection<ItemStack>): Boolean {
        items.forEach { if (! hasItem(inventory, it)) return false }
        return true
    }

    fun countItem(inventory: Inventory, material: Material, data: Short) = inventory.all(material).values
            .filter { it.durability == data }
            .sumBy { it.amount }

    fun countItem(inventory: Inventory, item: ItemStack) = countItem(inventory, item.type, item.durability)

    private fun countItems(inventory: Inventory, items: Array<ItemStack>): Map<ItemStack, Int> {
        val map = HashMap<ItemStack, Int>()
        items.forEach {
            map[it] = countItem(inventory, it)
        }
        return map
    }

    fun countItems(inventory: Inventory, items: Collection<ItemStack>): Map<ItemStack, Int> = countItems(inventory, items.toTypedArray())

    fun consumeItem(inventory: Inventory, mat: Material, amount: Int): Boolean {
        var count = amount
        val ammo = inventory.all(mat)
        val found = ammo.values.sumBy { it.amount }
        if (count > found) return false
        for (index in ammo.keys) {
            val stack = ammo[index] ?: continue
            val removed = Math.min(count, stack.amount)
            count -= removed
            if (stack.amount == removed) inventory.setItem(index, null) else stack.amount = (stack.amount - removed)
            if (count <= 0)
                break
        }
        return true
    }

    fun consumeItem(inventory: Inventory, mat: Material, amount: Int, data: Short): Boolean {
        var count = amount
        val ammo = inventory.all(mat)
        val found = ammo.values.filter { it.durability == data }.sumBy { it.amount }
        if (count > found) return false
        for (index in ammo.keys) {
            val stack = ammo[index] ?: continue
            if (stack.durability != data) continue
            val removed = Math.min(count, stack.amount)
            count -= removed
            if (stack.amount == removed) inventory.setItem(index, null) else stack.amount = (stack.amount - removed)
            if (count <= 0) break
        }
        return true
    }

    fun consumeItems(inventory: Inventory,list: Collection<ItemStack>) = list.forEach { consumeItem(inventory,it.type,it.amount, it.durability) }
    fun setLoreItemstack(item: ItemStack,list:List<String>): ItemStack {
        val itemMeta = item.itemMeta
        itemMeta.lore = list
        item.itemMeta = itemMeta
        return item
    }


    fun removeOneFromHand(player: Player){
        val item = player.itemInHand
        if(item.amount == 0)
            player.itemInHand = null
        else
            item.amount--
        player.itemInHand = item
    }
}
