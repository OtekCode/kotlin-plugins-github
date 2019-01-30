package pl.otekplay.fight

import org.bukkit.Bukkit
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Locations
import pl.otekplay.spawn.SpawnAPI

class FightListener(val plugin: FightPlugin) : Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val player = e.entity as? Player ?: return
        val bowAttack: Boolean
        val attacker = if (e.damager is Arrow) {
            val arrow = e.damager as Arrow
            if (arrow.shooter is Player) {
                bowAttack = true
                arrow.shooter as Player
            } else return
        } else if (e.damager is Player) {
            bowAttack = false
            e.damager as Player
        } else return
        val fight = plugin.manager.getFight(player.uniqueId) ?: return
        if (!plugin.manager.isFighting(fight)) {
            fight.clear()
        }
        fight.attack(attacker.uniqueId, e.damage, if (bowAttack) FightAttackType.BOW else FightAttackType.WEAPON)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerDeath(e: PlayerDeathEvent) {
        plugin.manager.getFight(e.entity.uniqueId)?.clear()
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        plugin.manager.registerFight(e.player.uniqueId)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        val fight = plugin.manager.getFight(player.uniqueId) ?: return
        if (plugin.manager.isFighting(fight)) {
            player.damage(20.0)
            Bukkit.broadcastMessage(plugin.config.messages.playerLoggedWhileAntyRelog.rep("%name%", player.name))
        }
        plugin.manager.unregisterFight(player.uniqueId)
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPlayerCommandPreprocess(e: PlayerCommandPreprocessEvent) {
        val player = e.player
        val fight = plugin.manager.getFight(player.uniqueId) ?: return
        if (!plugin.manager.isFighting(fight)) return
        if (player.hasPermission(plugin.config.permissionCombatDisabledBypass)) return
        val command = e.message.rep("/", "")
        plugin.config.messages.allowedCommmandsWhileCombat.forEach {
            if (command.startsWith(it)) return
        }
        player.sendMessage(plugin.config.messages.commandsWhileCombatAreDisabled)
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerMove(e: PlayerMoveEvent) {
        val fight = plugin.manager.getFight(e.player.uniqueId) ?: return
        if (!plugin.manager.isFighting(fight)) return
        if (!SpawnAPI.isPvPProtection(e.to)) return
        Locations.pushAwayFrom(SpawnAPI.spawnLocation, e.player, plugin.config.speedPushAwayFromSpawn)
    }
}