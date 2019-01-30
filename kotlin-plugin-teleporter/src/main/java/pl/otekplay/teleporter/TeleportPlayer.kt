package pl.otekplay.teleporter

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

class TeleportPlayer(
        plugin: TeleportPlugin,
        uniqueId: UUID,
        firstLocation: Location,
        moveCheck: Boolean,
        teleportTime: Long,
        private val playerToId: UUID,
        callback: TeleportCallback
) : Teleport(plugin, uniqueId, firstLocation, moveCheck, teleportTime, callback) {
    override fun teleport(player: Player) {
        this.plugin.teleporter.teleportPlayerToPlayer(player, Bukkit.getPlayer(playerToId) !!)
    }

    override fun checkOthers(): Boolean {
        val toPlayer = Bukkit.getPlayer(playerToId)
        if (toPlayer == null) {
            Bukkit.getPlayer(uniqueId).sendMessage(plugin.config.messages.teleportToMissPlayer)
            callback.cancelled(this)
            removeTeleport()
            return false
        }
        return true
    }

}