package pl.otekplay.drop

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.builders.ItemBuilder
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Items
import java.util.*

class DropMenu(val plugin: DropPlugin) {


    private fun replaceDropInfo(item: ItemStack, user: DropUser, drop: Drop) {
        val itemMeta = item.itemMeta
        val lore = arrayListOf(*plugin.config.dropLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%name%", drop.info.name)
                    .rep("%min%", drop.info.amount.min.toString())
                    .rep("%max%", drop.info.amount.max.toString())
                    .rep("%chance%", drop.info.chance.toString())
                    .rep("%perm%", drop.info.permission)
                    .rep("%playerexp%", drop.info.playerExp.toString())
                    .rep("%dropexp%", drop.info.dropExp.toString())
                    .rep("%enabled%", if (drop.isBlockedByUser(user)) plugin.config.menuDropDisabledString else plugin.config.menuDropEnabledString)
                    .rep("%collected%", user.getDrops(drop.item.id).toString())
                    .rep("%biomes%", Arrays.deepToString(drop.info.biomes.map { it.name }.toTypedArray()))
        }
        itemMeta.lore = lore
        item.itemMeta = itemMeta
    }

    fun open(player: Player) {
        val menu = Menu(plugin.config.menuOptions)
        val user = plugin.manager.getUser(player.uniqueId) ?: return
        plugin.manager.drops.forEach {
            val drop = it
            val itemStack = drop.item.generateItem(1) ?: return
            replaceDropInfo(itemStack, user, drop)
            menu.addItem(Item(itemStack, object : ItemClick {
                override fun click(player: Player) {
                    openItemDropMenu(player, drop, user)
                }
            }))
        }
        menu.open(player)
    }

    //DropMenu
    private fun openItemDropMenu(player: Player, drop: Drop, user: DropUser) {
        val menu = Menu(plugin.config.menuItemOptions)
        setHourItem(player, drop, user, menu)
        setBonusItem(player, drop, user, menu)
        setHeightItem(player, drop, user, menu)
        setPickAxeItem(player, drop, user, menu)
        setFortuneItem(player, drop, user, menu)
        setTurboItem(player, drop, user, menu)
        setPermItem(player, drop, user, menu)
        setOptionItem(player, drop, user, menu)
        setExitItem(player, drop, user, menu)
        menu.open(player)

    }

    //HourItem
    private fun setHourItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        if (!drop.time.enabled) return
        val item = plugin.config.menuHourItem
        val slot = item.slot
        val itemStack = item.item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuHourItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%startdate%", drop.time.startTime)
                    .rep("%now%", if (drop.time.isAvailable()) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%from%", drop.time.from)
                    .rep("%to%", drop.time.to)
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }

    //PickAxeItem
    private fun setPickAxeItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        if (!drop.info.pickAxe.check || drop.info.pickAxe.minMax.min == 0) return
        val slot = plugin.config.menuPickAxeItemSlot
        val itemStack = ItemBuilder(drop.info.pickAxe.getMinimalMaterialToDrop(), plugin.config.menuPickAxeItemName).buildItemStack()
        val lore = arrayListOf(*plugin.config.menuPickAxeItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%pickaxe%", if (drop.info.pickAxe.checkValidPickAxe(player.itemInHand)) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%enabled%", plugin.config.menuDropEnabledString)
                    .rep("%min%", drop.info.pickAxe.minMax.min.toString())
                    .rep("%max%", drop.info.pickAxe.minMax.max.toString())
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }

    //HeightItem
    private fun setHeightItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        if (!drop.info.height.check) return
        val item = plugin.config.menuHeightItem
        val slot = item.slot
        val itemStack = item.item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuHeightItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%height%", if (drop.info.height.isValidLocation(player.location)) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%enabled%", plugin.config.menuDropEnabledString)
                    .rep("%min%", drop.info.height.minMax.min.toString())
                    .rep("%max%", drop.info.height.minMax.max.toString())
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }

    //BonusItem
    private fun setBonusItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        val bonusItem = plugin.config.menuBonusItem
        val item = bonusItem.item
        val slot = bonusItem.slot
        val itemStack = item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuBonusItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%startdate%", drop.time.startTime)
                    .rep("%hours%", if (drop.time.enabled) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%perm%", if (player.hasPermission(drop.info.permission)) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%pickaxe%", if (drop.info.pickAxe.check) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%height%", if (drop.info.height.check) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%permbonus%", if (drop.perms.any { it.enabled }) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%turbo%", if (drop.turbo.enabled) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
                    .rep("%fortune%", if (drop.fortunes.any { it.enabled }) plugin.config.menuDropEnabledString else plugin.config.menuDropDisabledString)
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }

    //OptionItem
    private fun setOptionItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        val optionItem = plugin.config.menuOptionItem
        val item = optionItem.item
        val slot = optionItem.slot
        val itemStack = item.toItemStack()
        val mat = Material.getMaterial(drop.item.id)
        val lore = arrayListOf(*plugin.config.menuOptionItemLore.toTypedArray())
        lore.replaceAll { it.rep("%collected%", user.getDrops(mat).toString()).rep("%enabled%", if (user.isDisabled(mat)) plugin.config.menuDropDisabledString else plugin.config.menuDropEnabledString) }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore), object : ItemClick {
            override fun click(player: Player) {
                if (user.isDisabled(mat)) user.enableDrop(mat) else user.disableDrop(mat)
                setOptionItem(player, drop, user, menu)
                menu.open(player)
            }

        }))
    }

    //ExitItem
    private fun setExitItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        val exitItem = plugin.config.menuExitItem
        val item = exitItem.item
        val slot = exitItem.slot
        val itemStack = item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuExitItemLore.toTypedArray())
        lore.replaceAll { it.rep("", "") }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore), object : ItemClick {
            override fun click(player: Player) {
                open(player)
            }

        }))
    }

    //PermItem
    private fun setPermItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        val perm = drop.perms.filter { it.enabled }.filter { it.hasPermission(player) }.maxBy { it.priority } ?: return
        val permItem = plugin.config.menuPermissionItem
        val item = permItem.item
        val slot = permItem.slot
        val itemStack = item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuPermissionItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%perm%", perm.permission)
                    .rep("%enabled%", plugin.config.menuDropEnabledString)
                    .rep("%chance%", perm.chance.toString())
                    .rep("%playerexp%", perm.playerExp.toString())
                    .rep("%dropexp%", perm.dropExp.toString())
                    .rep("%amount%", perm.amount.toString())
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }

    //FortuneItem
    private fun setFortuneItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        val itemInHand = player.itemInHand
        val fortune = drop.fortunes.filter { it.enabled }.singleOrNull { it.checkValidPickAxe(itemInHand) } ?: return
        val fortuneItem = plugin.config.menuFortuneItem
        val item = fortuneItem.item
        val slot = fortuneItem.slot
        val itemStack = item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuFortuneItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%enabled%", plugin.config.menuDropEnabledString)
                    .rep("%chance%", fortune.chance.toString())
                    .rep("%playerexp%", fortune.playerExp.toString())
                    .rep("%dropexp%", fortune.dropExp.toString())
                    .rep("%amount%", fortune.amount.toString())
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }

    //TurboItem
    private fun setTurboItem(player: Player, drop: Drop, user: DropUser, menu: Menu) {
        if (user.turboTime < System.currentTimeMillis()) return
        val turbo = drop.turbo
        if (!turbo.enabled) return
        val turboItem = plugin.config.menuTurboItem
        val item = turboItem.item
        val slot = turboItem.slot
        val itemStack = item.toItemStack()
        val lore = arrayListOf(*plugin.config.menuTurboItemLore.toTypedArray())
        lore.replaceAll {
            it
                    .rep("%enabled%", plugin.config.menuDropEnabledString)
                    .rep("%chance%", turbo.chance.toString())
                    .rep("%playerexp%", turbo.playerExp.toString())
                    .rep("%dropexp%", turbo.dropExp.toString())
                    .rep("%amount%", turbo.amount.toString())
                    .rep("%time%", Dates.formatData(user.turboTime))
        }
        menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore)))
    }
}