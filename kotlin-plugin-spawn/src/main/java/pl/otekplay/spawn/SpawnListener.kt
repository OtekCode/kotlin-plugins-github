package pl.otekplay.spawn

import org.bukkit.Location
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.*
import org.spigotmc.event.player.PlayerSpawnLocationEvent

class SpawnListener(private val plugin: SpawnPlugin) : Listener {


    @EventHandler
    fun onPlayerDropItem(e: PlayerDropItemEvent) {
        val player = e.player
        if (canBuild(player, e.itemDrop.location)) return
        if (player.hasPermission(plugin.config.permissionAllowDropItemOnSpawn)) return
        player.sendMessage(plugin.config.messages.youCantDropItemsOnSpawn)
        e.isCancelled = true
    }

    @EventHandler
    fun onPlayerRespawn(e: PlayerRespawnEvent) {
        e.respawnLocation = plugin.spawnLocation
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerSpawn(e: PlayerSpawnLocationEvent) {
        if (!e.player.hasPlayedBefore()) e.spawnLocation = plugin.spawnLocation
    }

    private fun canBuild(player: Player, location: Location) = if (!plugin.isCuboidProtection(location)) true else player.hasPermission(plugin.config.permissionBypassBuild)

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) {
        if (canBuild(e.player, e.block.location)) return
        e.isCancelled = true
        e.player.sendMessage(plugin.config.messages.spawnBuildProtection)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) {
        if (canBuild(e.player, e.block.location)) return
        e.isCancelled = true
        e.player.sendMessage(plugin.config.messages.spawnBuildProtection)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerBucketFill(e: PlayerBucketFillEvent) {
        if (canBuild(e.player, e.blockClicked.location)) return
        e.isCancelled = true
        e.player.sendMessage(plugin.config.messages.spawnBuildProtection)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerBucketEmpty(e: PlayerBucketEmptyEvent) {
        if (canBuild(e.player, e.blockClicked.location)) return
        e.isCancelled = true
        e.player.sendMessage(plugin.config.messages.spawnBuildProtection)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onEntityExplode(e: EntityExplodeEvent) {
        e.blockList().removeIf { plugin.isCuboidProtection(it.location) }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onBlockExplode(e: BlockExplodeEvent) {
        e.blockList().removeIf { plugin.isCuboidProtection(it.location) }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onBlockPistonExtend(e: BlockPistonExtendEvent) {
        val bf = e.direction
        val block = e.block
        if (plugin.isCuboidProtection(block.location)) {
            e.isCancelled = true
            return
        }
        for (i in 1..15) {
            if (!plugin.isCuboidProtection(block.getRelative(bf, i).location)) continue
            e.isCancelled = true
            break
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val player = e.entity as? Player ?: return
        val attacker = if (e.damager is Arrow) {
            val arrow = e.damager as Arrow
            if (arrow.shooter is Player) {
                arrow.shooter as Player
            } else return
        } else if (e.damager is Player) {
            e.damager as Player
        } else return
        if (!plugin.isPvPProtection(player.location) && !plugin.isPvPProtection(attacker.location)) return
        e.isCancelled = true
        attacker.sendMessage(plugin.config.messages.spawnPvPProtection)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onEntityDamage(e: EntityDamageEvent) {
        val player = e.entity as? Player ?: return
        if (plugin.isPvPProtection(player.location)) e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerInteractBlock(e: PlayerInteractEvent) {
        if(!e.player.isSneaking) return
        if(e.action != Action.RIGHT_CLICK_BLOCK) return
        if(!e.player.hasPermission(plugin.config.permissionCheckTerrainInfo)) return
        val terrainSpawn = SpawnAPI.isCuboidProtection(e.clickedBlock.location)
        val terrainPvP = SpawnAPI.isPvPProtection(e.clickedBlock.location)
        if(terrainPvP) e.player.sendMessage(plugin.config.messages.terrainIsPvP)
        if(terrainSpawn) e.player.sendMessage(plugin.config.messages.terrainIsSpawn)

    }

}