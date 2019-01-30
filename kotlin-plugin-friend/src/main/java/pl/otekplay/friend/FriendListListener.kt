package pl.otekplay.friend

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import pl.otekplay.tag.PlayerTagEvent

class FriendListListener(val plugin: FriendPlugin) : Listener {


    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val uniqueId = e.player.uniqueId
        if (plugin.friendListManager.getFriendList(uniqueId) != null) return
        plugin.friendListManager.createFriendList(uniqueId)

    }


}