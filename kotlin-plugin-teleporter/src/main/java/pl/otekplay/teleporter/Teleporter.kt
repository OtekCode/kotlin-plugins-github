package pl.otekplay.teleporter

import com.google.common.cache.CacheBuilder
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class Teleporter(val plugin: TeleportPlugin) {

    val teleports = HashMap<UUID, Teleport>()
    val requestTo = CacheBuilder
            .newBuilder()
            .expireAfterWrite(plugin.config.messages.teleportRequestToTimeout, TimeUnit.MILLISECONDS)
            .build<UUID, UUID>() !!
    val requestHere = CacheBuilder
            .newBuilder()
            .expireAfterWrite(plugin.config.messages.teleportRequestHereTimeout, TimeUnit.MILLISECONDS)
            .build<UUID, UUID>() !!
    val lastRequestHere = CacheBuilder
            .newBuilder()
            .expireAfterWrite(plugin.config.messages.teleportLastRequestTimeout, TimeUnit.MILLISECONDS)
            .build<UUID, UUID>() !!
    val lastRequestTo = CacheBuilder
            .newBuilder()
            .expireAfterWrite(plugin.config.messages.teleportLastRequestTimeout, TimeUnit.MILLISECONDS)
            .build<UUID, UUID>() !!
    private val task = object : BukkitRunnable() {
        override fun run() {
            if (teleports.size == 0) return
            teleports.values.forEach { it.check() }
        }

    }

    fun teleportPlayerToPlayer(from: Player, to: Player) {
        from.teleport(to, PlayerTeleportEvent.TeleportCause.PLUGIN)
    }

    fun teleportPlayerToLocation(from: Player, location: Location) {
        from.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN)
    }


    fun teleportPlayerToPlayerWithDelay(from: Player, to: Player, delay: Long, moveCheck: Boolean = true, callback: TeleportCallback = TeleportAPI.defaultRequest) {
        if (moveCheck) from.sendMessage(plugin.config.messages.teleportInfoDontMove)
        if (hasTeleport(from)) return from.sendMessage(plugin.config.messages.teleportYouAlreadyHaveOne)
        teleports[from.uniqueId] = TeleportPlayer(plugin, from.uniqueId, from.location, moveCheck, delay, to.uniqueId, callback)
    }

    fun teleportPlayerToLocationWithDelay(from: Player, to: Location, delay: Long, moveCheck: Boolean = true, callback: TeleportCallback = TeleportAPI.defaultRequest) {
        if (moveCheck) from.sendMessage(plugin.config.messages.teleportInfoDontMove)
        if (hasTeleport(from)) return from.sendMessage(plugin.config.messages.teleportYouAlreadyHaveOne)
        teleports[from.uniqueId] = TeleportLocation(plugin, from.uniqueId, from.location, moveCheck, delay, to, callback)
    }

    private fun hasTeleport(player: Player) = teleports.containsKey(player.uniqueId)


    internal fun removeTeleport(uniqueId: UUID) = teleports.remove(uniqueId)


    fun start() {
        plugin.taskTimerSync(task, plugin.config.teleportTaskScheduleTime)
    }

    fun stop() {
        task.cancel()
        teleports.clear()
    }


}