package pl.otekplay.teleporter

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import java.util.*

abstract class Teleport(val plugin: TeleportPlugin,
                        val uniqueId: UUID,
                        private val firstLocation: Location,
                        private val moveCheck: Boolean = false,
                        private val teleportTime: Long,
                       internal val callback: TeleportCallback) {

    init {
        if(teleportTime > 60000){
            plugin.logger.warning("Teleport delay is set above 60000, is so long, are you sure you need that?")
        }
    }

    private val startTime = System.currentTimeMillis()
    private var lastMessageTime = 0L

    internal fun removeTeleport() {
        plugin.logger.info("Usuwanie teleportu kurwa mac.")
        this.plugin.teleporter.removeTeleport(this.uniqueId)
    }


    private fun sendTeleportInfo(player: Player) {
        if ((System.currentTimeMillis() - startTime) < plugin.config.messages.teleportInfoDelay) {
            return
        }
        if ((System.currentTimeMillis() - lastMessageTime) < plugin.config.messages.teleportInfoDelay) {
            return
        }
        val time = (teleportTime + startTime) - System.currentTimeMillis()
        var stringTime = time.toString()
        stringTime = if (time > 1000) stringTime.replaceRange(1, 1, "," + stringTime[1]) else "0,$stringTime"
        lastMessageTime = System.currentTimeMillis()
        player.sendMessage(plugin.config.messages.teleportInSeconds.rep("%time%", stringTime.substring(0,3)))
    }

    internal fun check() {
        val player = Bukkit.getPlayer(uniqueId)
        if (player == null) {
            callback.cancelled(this)
            removeTeleport()
            return
        }
        if(!player.hasPermission(plugin.config.permissionInstaTeleport)) {
            if (moveCheck) {
                val playerLoc = player.location
                if (firstLocation.blockX != playerLoc.blockX || firstLocation.blockZ != playerLoc.blockZ) {
                    player.sendMessage(plugin.config.messages.teleportedCancelledByMove)
                    callback.cancelled(this)
                    removeTeleport()
                    return
                }
            }
            if (! checkOthers()) {
                return
            }
            if ((teleportTime + startTime) > System.currentTimeMillis()) {
                sendTeleportInfo(player)
                return
            }
        }

        teleport(player)
        player.sendMessage(plugin.config.messages.teleportedSucessful)
        callback.complete(this)
        removeTeleport()
    }

    internal fun cancel(){

    }

    internal open fun checkOthers(): Boolean {
        return true
    }

   internal abstract fun teleport(player: Player)


}