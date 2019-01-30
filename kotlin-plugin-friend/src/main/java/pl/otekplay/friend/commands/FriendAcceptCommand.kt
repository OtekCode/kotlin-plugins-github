package pl.otekplay.friend.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import com.comphenix.protocol.ProtocolLibrary
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.friend.FriendPlugin

@CommandPermission("command.friend")
@CommandAlias("friend")
class FriendAcceptCommand(val plugin : FriendPlugin) : PluginCommand() {

    @CommandPermission("command.friend.accept")
    @Subcommand("accept")
    fun onCommand(player: Player, offlinePlayer: OfflinePlayer){
        val otherList = plugin.friendListManager.getFriendList(offlinePlayer.uniqueId)?: return
        if(otherList.friends.containsKey(player.uniqueId)) return player.sendMessage("jestescie juz przyjaculmi.(tak celowo przez u)")
        if(!otherList.invites.contains(player.uniqueId)) return player.sendMessage("chuj, cipa, nie zaprosil cie ${offlinePlayer.name} go juz.")
        val playerList = plugin.friendListManager.getFriendList(player.uniqueId)?: return
        otherList.invites.remove(player.uniqueId)
        otherList.friends[player.uniqueId] = System.currentTimeMillis()
        playerList.friends[offlinePlayer.uniqueId] = System.currentTimeMillis()
        player.sendMessage("zgodziles sie na przyjaciulstwo z ${offlinePlayer.name}")
        if (offlinePlayer.isOnline) {
            val oPlayer = offlinePlayer.player
            oPlayer.sendMessage("chuj ${player.name} zgodzil sie i jestescie przyjaciulmi")
            val manager = plugin.friendListManager
            manager.reloadSkinForPlayer(player,oPlayer)
            manager.reloadSkinForPlayer(oPlayer,player)
        }

    }

}