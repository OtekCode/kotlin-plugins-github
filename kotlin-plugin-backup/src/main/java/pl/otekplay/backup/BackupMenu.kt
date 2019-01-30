package pl.otekplay.backup

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Items
import java.util.*

class BackupMenu(val plugin: BackupPlugin) {

    //BACKUP LIST MENU

    fun openMenuWithListBackups(player: Player, backup: PlayerBackup) {
        val menu = Menu(plugin.config.optionsMenuBackupList)
        backup.backups.forEach {
            menu.addItem(Item(buildBackupItem(it), object : ItemClick {
                override fun click(player: Player) {
                    openMenuWithBackupOptions(player, backup.uniqueId, it, menu)
                }
            }))
        }
        menu.open(player)
    }

    private fun buildBackupItem(backup: Backup): ItemStack {
        val item = plugin.config.backupMenuItem.toItemStack()
        val lore = item.itemMeta.lore
        lore.replaceAll {
            it
                    .rep("%id%", backup.id)
                    .rep("%date%", Dates.formatData(backup.time))
                    .rep("%type%", backup.type.name)
                    .rep("%contents%", backup.contents.size.toString())
                    .rep("%armor%", backup.armor.toArray().filterNotNull().size.toString())
                    .rep("%enderchest%", backup.chest.size.toString())
        }
        Items.setLoreItemstack(item, lore)
        return item
    }

    //BACKUP OPTIONS MENU

    fun openMenuWithBackupOptions(player: Player, backupPlayerId: UUID, backup: Backup, listBackupMenu: Menu) {
        val menu = Menu(plugin.config.optionsMenuBackup)
        menu.name = menu.name.rep("%id%",backup.id)
        setMenuItemShowItems(menu, plugin.config.menuItemContents,plugin.config.menuOptionsItemsShowInventory, backup.contents)
        setMenuItemShowItems(menu, plugin.config.menuItemArmor,plugin.config.menuOptionsItemsShowArmor, backup.armor.toMap())
        setMenuItemShowItems(menu, plugin.config.menuItemEnderchest,plugin.config.menuOptionsItemsShowEnderchest, backup.chest)
        setMenuItemUse(menu, backupPlayerId, backup)
        setMenuItemBack(menu, listBackupMenu)
        menu.open(player)
    }

    private fun setMenuItemUse(menu: Menu, uniqueId: UUID, backup: Backup) {
        val menuItem = plugin.config.menuItemUse
        val item = Item(menuItem.item.toItemStack(), object : ItemClick {
            override fun click(player: Player) {
                val backupPlayer = Bukkit.getOfflinePlayer(uniqueId)
                if (backupPlayer.isOnline) {
                    plugin.manager.useBackup(backupPlayer.player, backup)
                    player.sendMessage(plugin.config.messages.youBackupPlayer.rep("%name%", backupPlayer.name).rep("%date%",Dates.formatData(backup.time)))
                } else {
                    player.sendMessage(plugin.config.messages.playerIsOffline.rep("%name%", backupPlayer.name))
                }
                player.closeInventory()
            }
        })
        menu.setItem(menuItem.slot, item)
    }


    private fun setMenuItemBack(menu: Menu, parentMenu: Menu) {
        val menuItem = plugin.config.menuItemBackFromOptions
        val item = Item(menuItem.item.toItemStack(), object : ItemClick {
            override fun click(player: Player) {
                parentMenu.open(player)
            }
        })
        menu.setItem(menuItem.slot, item)
    }


    private fun setMenuItemShowItems(menu: Menu, menuItem: ConfigMenuItem, options: ConfigMenuOptions, items:Map<Int,ConfigEnchantedItem>) {
        val item = Item(menuItem.item.toItemStack(), object : ItemClick {
            override fun click(player: Player) {
                openMenuWithItems(player, menu,options, items)
            }
        })
        menu.setItem(menuItem.slot, item)
    }


    //BACKUP ITEMS MENU

    private fun openMenuWithItems(player: Player, optionsMenu: Menu,options: ConfigMenuOptions, items:Map<Int,ConfigEnchantedItem>) {
        val menu = Menu(options)
        items.forEach { t, u -> menu.setItem(t,Item(u.toItemStack())) }
        addMenuItemBack(menu,optionsMenu)
        menu.open(player)
    }


    private fun addMenuItemBack(menu: Menu, optionsMenu: Menu) {
        val menuItem = plugin.config.menuItemBackFromItems
        val item = Item(menuItem.toItemStack(), object : ItemClick {
            override fun click(player: Player) {
                optionsMenu.open(player)
            }
        })
        menu.setAsLast(item)
    }


}