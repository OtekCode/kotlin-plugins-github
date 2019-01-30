package pl.otekplay.teleporter

import org.bukkit.Location
import org.bukkit.entity.Player

object TeleportAPI {

    internal lateinit var plugin: TeleportPlugin
    internal val defaultRequest =
            object : TeleportCallback {
                override fun complete(teleport: Teleport) {

                    println("default complete call")
                }

                override fun cancelled(teleport: Teleport) {
                    println("default cancel call")
                }
            }

    fun teleportPlayerToPlayer(from: Player, to: Player) {
        plugin.teleporter.teleportPlayerToPlayer(from, to)
    }

    fun teleportPlayerToLocation(from: Player, location: Location) {
        plugin.teleporter.teleportPlayerToLocation(from, location)
    }

    fun teleportPlayerToPlayerWithDelay(from: Player, to: Player, delay: Long, moveCheck: Boolean,callback: TeleportCallback = defaultRequest) {
        plugin.teleporter.teleportPlayerToPlayerWithDelay(from, to, delay, moveCheck, callback)
    }

    fun teleportPlayerToLocationWithDelay(from: Player, to: Location, delay: Long, moveCheck: Boolean,callback: TeleportCallback = defaultRequest) {
        plugin.teleporter.teleportPlayerToLocationWithDelay(from, to, delay, moveCheck, callback)
    }

}