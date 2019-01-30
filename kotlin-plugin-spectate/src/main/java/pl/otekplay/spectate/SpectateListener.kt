package pl.otekplay.spectate

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.plugin.rep
import pl.otekplay.spawn.SpawnAPI
import java.util.*

class SpectateListener(private val plugin: SpectatePlugin) : Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val player = e.entity
        endSpectators(player, plugin.config.messages.playerDiedWhileWasSpectate.rep("%name%", player.name))
        val killer = player.killer ?: return
        player.sendMessage(plugin.config.messages.youSpectateAfterDeath.rep("%name%", killer.name))
        killer.sendMessage(plugin.config.messages.youGotSpectated.rep("%name%", player.name))
        reviveAndSpectate(player.uniqueId, killer.uniqueId)
    }

    private fun reviveAndSpectate(uniqueId: UUID, killerId: UUID) {
        val player = Bukkit.getPlayer(uniqueId) ?: return
        val killer = Bukkit.getPlayer(killerId) ?: return
        player.health = 20.0
        player.activePotionEffects.clear()
        player.foodLevel = 20
        player.gameMode = GameMode.SPECTATOR
        plugin.taskLaterSync(object : BukkitRunnable() {
            override fun run() {
                player.spectatorTarget = killer
            }
        }, 1)
        teleportAfterDelay(player.uniqueId)
    }

    private fun teleportAfterDelay(uniqueId: UUID) {
        plugin.taskLaterSync(object : BukkitRunnable() {
            override fun run() {
                endSpectate(Bukkit.getPlayer(uniqueId) ?: return)
            }
        }, plugin.config.spectateTime)
    }

    private fun endSpectate(player: Player) {
        if(player.gameMode != GameMode.SPECTATOR) return
        player.gameMode = GameMode.SURVIVAL
        if (player.spectatorTarget != null) player.spectatorTarget = null
        player.teleport(SpawnAPI.spawnLocation)
        player.sendMessage(plugin.config.messages.youGotBackToSpawnAfterSpectate)
    }

    private fun endSpectators(player: Player, message: String) {
        Bukkit.getOnlinePlayers()
                .filter { it.gameMode == GameMode.SPECTATOR }
                .filter { it.spectatorTarget == player }
                .forEach {
                    endSpectate(it)
                    it.sendMessage(message)
                }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerToggleSneak(e: PlayerToggleSneakEvent) {
        if (e.player.gameMode == GameMode.SPECTATOR) endSpectate(e.player)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (e.player.gameMode == GameMode.SPECTATOR) endSpectate(e.player)
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(e: PlayerQuitEvent) {
        endSpectators(e.player, plugin.config.messages.playerDiedWhileWasSpectate.rep("%name%", e.player.name))
    }
}