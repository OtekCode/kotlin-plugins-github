package pl.otekplay.teleporter

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*


class TeleportLocation(
        plugin: TeleportPlugin,
        uniqueId: UUID,
        firstLocation: Location,
        moveCheck: Boolean,
        teleportTime: Long,
        private val location: Location,
        callback: TeleportCallback
) : Teleport(plugin, uniqueId, firstLocation, moveCheck, teleportTime,callback) {

    override fun teleport(player: Player) {
        this.plugin.teleporter.teleportPlayerToLocation(player, location)
    }


}