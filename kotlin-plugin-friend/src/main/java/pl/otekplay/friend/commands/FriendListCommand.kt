package pl.otekplay.friend.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import com.comphenix.protocol.wrappers.BlockPosition
import org.apache.commons.lang3.RandomUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.friend.FriendPlugin
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.util.Locations
import pl.otekplay.plugin.util.Randoms
import pl.otekplay.plugin.wrappers.WrapperPlayServerBlockBreakAnimation

@CommandPermission("command.friend")
@CommandAlias("friend")
class FriendListCommand(val plugin: FriendPlugin) : PluginCommand() {

    @CommandPermission("command.friend.list")
    @Subcommand("list")
    fun onCommand(player: Player) {
        val friend = plugin.friendListManager.getFriendList(player.uniqueId) ?: return
        player.sendMessage("friendy")
        friend.friends.keys.map { Bukkit.getOfflinePlayer(it).name }.forEach { player.sendMessage(it.toString()) }
        player.sendMessage("invites")
        friend.invites.map { Bukkit.getOfflinePlayer(it).name }.forEach { player.sendMessage(it) }
        Locations.getWallsOnGround(player.location,10).forEach {
            val wrapper = WrapperPlayServerBlockBreakAnimation()
            wrapper.entityID = player.entityId
            wrapper.location = BlockPosition(it.blockX,it.blockY,it.blockZ)
            wrapper.destroyStage = Randoms.getRandomInt(0,9)
            wrapper.sendPacket(player)
        }
    }

}