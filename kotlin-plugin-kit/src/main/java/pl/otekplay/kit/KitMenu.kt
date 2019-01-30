package pl.otekplay.kit

import org.bukkit.entity.Player
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Items

class KitMenu(private val plugin: KitPlugin) {

    fun openKitMenu(player: Player) {
        val menu = Menu(plugin.config.menuOptions)
        val user = plugin.manager.getUser(player.uniqueId) ?: return
        plugin.manager.kits.forEach {
            val kit = it
            val slot = it.menuItem.slot
            val item = it.menuItem.item
            val itemStack = item.toItemStack()
            val lore = arrayListOf(*itemStack.itemMeta.lore.toTypedArray())
            val canTake = user.canTake(kit)
            val cooldown = user.getCooldownTime(kit)
            val cooldownString = if (! canTake) Dates.formatData(cooldown) else plugin.config.menuKitCooldownNoExist
            lore.replaceAll {
                it
                        .rep("%perm%", if (player.hasPermission(kit.permission)) plugin.config.menuKitEnabledString else plugin.config.menuKitDisabledString)
                        .rep("%startdate%", kit.startDate)
                        .rep("%cooldown%", cooldownString)
                        .rep("%available%", if (canTake) plugin.config.menuKitEnabledString else plugin.config.menuKitDisabledString)
            }
            menu.setItem(slot, Item(Items.setLoreItemstack(itemStack, lore), object : ItemClick {
                override fun click(player: Player) {
                    if(it.startDateLong > System.currentTimeMillis()) return player.sendMessage(plugin.config.messages.kitIsNotAvailable.rep("%date%",it.startDate))
                    if (! player.hasPermission(it.permission)) return player.sendMessage(plugin.config.messages.youDontHavePermissionToKit)
                    if (! user.canTake(it)) return player.sendMessage(plugin.config.messages.cantTakeKitDelayed.rep("%cooldown%", cooldownString))
                    user.takeKit(kit)
                    player.sendMessage(plugin.config.messages.youTakeKit)
                    Items.addItems(player, kit.list.map { it.toItemStack() })
                    openKitMenu(player)
                }

            }))
        }
        menu.open(player)
    }
}