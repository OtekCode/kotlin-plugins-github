package pl.otekplay.chat

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import pl.otekplay.plugin.rep
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class ChatListener(private val plugin: ChatPlugin) : Listener {

    val map = ConcurrentHashMap<UUID, Long>()

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun onAsyncPlayerChat(e: AsyncPlayerChatEvent) {
        e.isCancelled = true
        val player = e.player
        if (!player.hasPermission(plugin.config.permissionChatLockedBypass)) if (plugin.config.chatLocked) return e.player.sendMessage(plugin.config.messages.chatIsDisabled)

        if (!player.hasPermission(plugin.config.permissionChatDelayBypass)) {
            val nextMessageTime = map[player.uniqueId]?: 0L + plugin.config.delayTimeChat
            if (nextMessageTime > System.currentTimeMillis()) return e.player.sendMessage(plugin.config.messages.youGotDelayChat.rep("%time%", "${TimeUnit.MILLISECONDS.toSeconds(nextMessageTime - System.currentTimeMillis())}"))
            map[player.uniqueId] = System.currentTimeMillis() + plugin.config.delayTimeChat
        }
        val sender = e.player
        val format = plugin.config.customFormats.filter { sender.hasPermission(it.permission) }.maxBy { it.priority }?.format
                ?: plugin.config.formatChat
        val replacedFormat = format.rep("%nickname%", sender.name).rep("%message%", e.message)
        e.recipients.map { ChatFormatEvent(sender, it, replacedFormat) }.forEach {
            plugin.callEvent(it)
            it.receiver.sendMessage(it.format)
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        map[e.player.uniqueId] = System.currentTimeMillis()
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        map.remove(e.player.uniqueId)
    }
}