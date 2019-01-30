package pl.otekplay.trade

import com.google.common.cache.CacheBuilder
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import pl.otekplay.plugin.rep
import pl.otekplay.spawn.SpawnAPI
import java.util.*
import java.util.concurrent.TimeUnit


class TradeListener(private val plugin: TradePlugin) : Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.clickedInventory == null) return
        val slot = e.slot
        val player = e.whoClicked as Player
        val playerView = player.openInventory
        val inventory = playerView.topInventory
        if (inventory.holder == null) return
        val holder = if (inventory.holder is TradeHolder) inventory.holder as TradeHolder else return
        val trade = holder.trade
        if (inventory == e.clickedInventory) trade.clickMenu(player, slot) else trade.clickInventory(player, slot)
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryClose(e: InventoryCloseEvent) {
        val inventory = e.inventory
        val holder = inventory.holder as? TradeHolder ?: return
        holder.trade.closeTrade()
    }

    private val invites = CacheBuilder.newBuilder()
            .expireAfterWrite(plugin.config.inviteTradeTime, TimeUnit.MILLISECONDS)
            .build<UUID, UUID>()

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerInteractEntity(e: PlayerInteractAtEntityEvent) {
        if (e.rightClicked !is Player || ! e.player.isSneaking) return
        val player = e.player
        val clicked = e.rightClicked as Player
        if (! SpawnAPI.isCuboidProtection(player.location)) return player.sendMessage(plugin.config.messages.youNeedBeOnSpawn)
        if (! SpawnAPI.isCuboidProtection(clicked.location)) return player.sendMessage(plugin.config.messages.playerNeedBeOnSpawn.replace("%name%", clicked.name))
        if (invites.getIfPresent(clicked.uniqueId) == player.uniqueId) {
            if (player.location.distance(clicked.location) > plugin.config.tradeMaxDistanceBeetwenPlayers) return player.sendMessage(plugin.config.messages.playerIsTooFar)
            Trade(plugin, clicked, player)
            invites.invalidate(player.uniqueId)
            invites.invalidate(clicked.uniqueId)
            return
        }
        val offeredUniqueId = invites.getIfPresent(player.uniqueId)
        if (offeredUniqueId == clicked.uniqueId) return player.sendMessage(plugin.config.messages.youOfferedAlreadyTradeToHim.replace("%name%",clicked.name))
        invites.put(player.uniqueId, clicked.uniqueId)
        player.sendMessage(plugin.config.messages.youOfferedPlayerTrade.rep("%name%", clicked.name))
        clicked.sendMessage(plugin.config.messages.playerOfferedYouTrade.rep("%name%", player.name))
    }
}
