package pl.otekplay.randomteleporter

import com.google.common.cache.CacheBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.material.Button
import org.spigotmc.event.player.PlayerSpawnLocationEvent
import pl.otekplay.plugin.util.Items
import pl.otekplay.spawn.SpawnAPI
import java.util.concurrent.TimeUnit

class RandomTeleportListener(private val plugin: RandomTeleportPlugin) : Listener {

    private val cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(plugin.config.randomTeleportButtonCooldown, TimeUnit.MILLISECONDS)
            .build<Location, Long>() !!

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.clickedBlock == null) return
        if (e.clickedBlock.type == Material.STONE_BUTTON || e.clickedBlock.type == Material.WOOD_BUTTON) {
            if (! SpawnAPI.isCuboidProtection(e.clickedBlock.location)) return
            val player = e.player
            val locationButton = e.clickedBlock.location
            val button = e.clickedBlock.state.data as Button
            val block = e.clickedBlock.getRelative(button.attachedFace)
            val type = block.type
            if (plugin.config.singleButtonBlocksId.contains(type.id)) return executeRandomTeleport(player, locationButton, false)
            if (plugin.config.groupButtonBlocksId.contains(type.id)) return executeRandomTeleport(player, locationButton, true)
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerSpawnLocation(e: PlayerSpawnLocationEvent) {
        if (! plugin.config.randomTeleportOnFirstJoin) return
        if (e.player.hasPlayedBefore()) return
        e.spawnLocation = plugin.manager.findSafeTeleportLocation()
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (! plugin.config.randomTeleportOnFirstJoin) return
        if (e.player.hasPlayedBefore()) return
        Items.addItems(e.player, plugin.config.itemsAfterTeleport.map { it.toItemStack() })
        e.player.sendMessage(plugin.config.messages.youGotRandomTeleportFirstJoin)
    }

    private fun executeRandomTeleport(player: Player, location: Location, group: Boolean) {
        if (cache.getIfPresent(location) != null) {
            player.sendMessage(plugin.config.messages.randomTeleportIsOnCooldown)
            return
        }
        cache.put(location, System.currentTimeMillis())
        if (group)
            executeGroupRandomTeleport(Bukkit.getOnlinePlayers().filter { player.location.distance(it.location) <= plugin.config.groupTeleportRangeDistance }.plus(player))
        else
            executeAloneRandomTeleport(player, plugin.manager.findSafeTeleportLocation())
    }


    private fun executeAloneRandomTeleport(player: Player, location: Location) {
        player.teleport(location)
        player.sendMessage(plugin.config.messages.youGotRandomTeleported)
        Items.addItems(player, plugin.config.itemsAfterTeleport.map { it.toItemStack() })
    }

    private fun executeGroupRandomTeleport(players: List<Player>) {
        val location = plugin.manager.findSafeTeleportLocation()
        players.forEach { executeAloneRandomTeleport(it, location) }
    }
}