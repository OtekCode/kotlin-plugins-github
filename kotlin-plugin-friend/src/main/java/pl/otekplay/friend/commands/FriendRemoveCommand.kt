package pl.otekplay.friend.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import com.comphenix.protocol.ProtocolLibrary
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.friend.FriendPlugin
import pl.otekplay.plugin.api.PluginCommand

@CommandPermission("command.friend")
@CommandAlias("friend")
class FriendRemoveCommand (val plugin : FriendPlugin): PluginCommand() {

    @CommandPermission("command.friend.remove")
    @Subcommand("remove")
    fun onCommand(player: Player,offlinePlayer: OfflinePlayer){
        val friendList = plugin.friendListManager.getFriendList(player.uniqueId)?: return
        if(!friendList.friends.containsKey(offlinePlayer.uniqueId)) return player.sendMessage("nie jestescie przyjaculmi, nie wyjebuj go.")
        val otherList = plugin.friendListManager.getFriendList(offlinePlayer.uniqueId)?: return
        friendList.friends.remove(offlinePlayer.uniqueId)
        otherList.friends.remove(player.uniqueId)
        player.sendMessage("wyrzuciles ujka ${offlinePlayer.name} z przyjaciul")
        if (offlinePlayer.isOnline) {
            val oPlayer = offlinePlayer.player
            val manager = plugin.friendListManager
            manager.reloadSkinForPlayer(player,oPlayer)
            manager.reloadSkinForPlayer(oPlayer,player)
        }
    }
}