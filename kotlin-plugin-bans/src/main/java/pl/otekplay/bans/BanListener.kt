package pl.otekplay.bans

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class BanListener(private val plugin: BanPlugin) : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onAsyncPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        val ip = e.address.hostAddress
        val ipBan = plugin.manager.getBan(ip, BanType.IP)
        val idBan = plugin.manager.getBan(e.uniqueId.toString(), BanType.PLAYER)

        val ban = ipBan ?: idBan ?: return
        val message = arrayListOf(*plugin.config.formatLoginDisallowBanned.toTypedArray())
        message.replaceAll { ban.replaceString(it) }
        e.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
        e.kickMessage = message.joinToString { "\n" + it }
    }
}