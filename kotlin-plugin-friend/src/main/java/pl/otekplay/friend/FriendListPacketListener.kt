package pl.otekplay.friend

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import pl.otekplay.plugin.wrappers.WrapperPlayServerNamedEntitySpawn
import pl.otekplay.plugin.wrappers.WrapperPlayServerPlayerInfo

class FriendListPacketListener(private val friendPlugin: FriendPlugin) : PacketAdapter(friendPlugin.pluginLoader.loaderPlugin, PacketType.Play.Server.PLAYER_INFO) {


    override fun onPacketSending(event: PacketEvent) {
        val packet = event.packet
        val wrapper = WrapperPlayServerPlayerInfo(packet)
        if(wrapper.action != EnumWrappers.PlayerInfoAction.ADD_PLAYER) return
        val friendlist = friendPlugin.friendListManager.getFriendList(event.player.uniqueId) ?: return
        wrapper.data = wrapper.data.map {
            if(friendlist.friends.containsKey(it.profile.uuid)){
                it
            }else{
                PlayerInfoData(it.profile.withName("[UKRYTY]"),
                        it.latency,
                        it.gameMode, it.displayName)
            }
        }
    }
}