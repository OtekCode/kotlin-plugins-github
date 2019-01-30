package pl.otekplay.friend

import com.comphenix.protocol.wrappers.*
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.plugin.wrappers.WrapperPlayServerEntityDestroy
import pl.otekplay.plugin.wrappers.WrapperPlayServerNamedEntitySpawn
import pl.otekplay.plugin.wrappers.WrapperPlayServerPlayerInfo
import java.util.*

class FriendListManager(val plugin: FriendPlugin) {
    private val friendLists = HashMap<UUID, FriendList>()


    fun getFriendList(uniqueId: UUID) = friendLists[uniqueId]

    fun createFriendList(uniqueId: UUID) = run { friendLists[uniqueId] = FriendList(uniqueId) }

    fun reloadSkinForPlayer(player: Player,requster:Player) {
        requster.hidePlayer(player)
        requster.showPlayer(player)
    }


}