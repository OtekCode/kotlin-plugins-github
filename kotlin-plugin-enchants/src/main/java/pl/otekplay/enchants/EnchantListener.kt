package pl.otekplay.enchants

import net.minecraft.server.v1_8_R3.*
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.enchantment.PrepareItemEnchantEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import org.bukkit.Bukkit.getScheduler


class EnchantListener(private val plugin: EnchantPlugin) : Listener {

    private val random = Random()
    private val lapis = ItemStack(Material.INK_SACK, 64, 4.toShort())
//
//    @EventHandler
//    fun onEnchantClick(e: PlayerInteractEvent) {
//        if (e.action != Action.RIGHT_CLICK_BLOCK) return
//        if (e.clickedBlock == null) return
//        if (e.clickedBlock.type != Material.ENCHANTMENT_TABLE) return
//        val player = e.player
//        openEnchant(player, e.clickedBlock.location)
//        e.isCancelled = true
//    }
//
//    fun openEnchant(player: Player, location: Location) {
//        val human = player as CraftHumanEntity
//        human.handle.openTileEntity(TileEnchantTable(location) as ITileEntityContainer?)
//        human.handle.activeContainer.checkReachable = false
//    }

    @EventHandler
    fun onLapisClick(e: InventoryClickEvent) {// Prevent them from stealing lapis
        if (!plugin.config.autoLapisEnchant) return
        if (
                e.clickedInventory != null &&
                e.clickedInventory.type == InventoryType.ENCHANTING &&
                e.rawSlot == 1) e.isCancelled = true
    }

    @EventHandler
    fun onEnchantOpen(e: InventoryOpenEvent) {
        if (!plugin.config.autoLapisEnchant) return
        if (e.inventory != null && e.inventory.type == InventoryType.ENCHANTING) e.inventory.setItem(1, lapis.clone())
    }

    @EventHandler
    fun onEnchantClose(e: InventoryCloseEvent) {
        if (!plugin.config.autoLapisEnchant) return
        if (e.inventory != null && e.inventory.type == InventoryType.ENCHANTING) e.inventory.setItem(1, null)
    }

    @EventHandler
    fun onPrepareEnchant(e: PrepareItemEnchantEvent) {
        plugin.logger.info("teraz prepare!")
        val view = e.view as CraftInventoryView
        val table = view.handle as ContainerEnchantTable
        if (plugin.config.randomSeedPerPrepare) {
            table.f = random.nextInt()// Set the enchantment seed
        }
        if (plugin.config.clearEnchantNames) {
            plugin.taskLaterSync(object : BukkitRunnable() {
                override fun run() {
                    for (index in table.h.indices) {
                        table.h[index] = -1
                    }
                }
            }, 1)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onEnchantItem(e: EnchantItemEvent) {
        println("teraz enchant kurwa.")
        println(e.expLevelCost)
        println(e.whichButton())
        e.enchantsToAdd.forEach {
            val max = plugin.config.enchants[it.key.id] ?: return@forEach
            if (it.value > max) e.enchantsToAdd[it.key] = max
        }
        plugin.taskLaterSync(object : BukkitRunnable() {
            override fun run() {
                e.enchanter.level = e.enchanter.level - (e.expLevelCost - (64 - e.inventory.getItem(1).amount))
                if (!plugin.config.autoLapisEnchant) return
                e.inventory.setItem(1, ItemStack(Material.INK_SACK, 64, 4.toShort()))
            }
        }, 1)
    }

}
