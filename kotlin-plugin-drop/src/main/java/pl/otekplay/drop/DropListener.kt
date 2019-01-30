package pl.otekplay.drop

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Items

class DropListener(private val plugin: DropPlugin) : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (plugin.manager.getUser(e.player.uniqueId) != null) return
        plugin.manager.createUser(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockBreakHigh(e: BlockBreakEvent) {
        val block = e.block
        if (!plugin.config.breakBlockedIds.contains(block.typeId)) return
        e.isCancelled = true
        block.type = Material.AIR
        e.player.sendMessage(plugin.config.messages.thisBlockIsBlocked)
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreakHighest(e: BlockBreakEvent) {
        val player = e.player
        val block = e.block
        val user = plugin.manager.getUser(player.uniqueId) ?: return
        val avaDrops = plugin.manager.getAvailableDrops(player, user, block)
        val drops = plugin.manager.getDrops(player, user, avaDrops)
        if (drops.isEmpty()) return
        drops.forEach { player.giveExp(it.playerExp) }
        drops.forEach { user.exp = it.dropExp + user.exp }
        if (plugin.manager.shouldUpgradeDropLevel(user)) {
            user.upgradeLevel()
            player.sendMessage(plugin.config.messages.youUpgradedYourDropLevel.rep("%level%", user.level.toString()))
        }
        val items = drops.filter { it.itemStack != null }
        if (items.isEmpty()) return user.updateEntity()
        items.forEach {
            val item = it.itemStack ?: return@forEach
            player.sendMessage(
                    plugin.config.messages.youDroppedItem
                            .rep("%name%", it.name)
                            .rep("%playerExp%", it.playerExp.toString())
                            .rep("%exp%", it.dropExp.toString())
                            .rep("%amount%", item.amount.toString())
            )
            user.collectDrops(item.type, item.amount.toLong())
            Items.addItem(player, item)
        }
        user.updateEntity()
    }


}