package pl.otekplay.friend.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.friend.FriendPlugin
import pl.otekplay.plugin.api.PluginCommand

@CommandPermission("command.friend")
@CommandAlias("friend")
class FriendInviteCommand(val plugin: FriendPlugin) : PluginCommand() {

    @CommandPermission("command.friend.invite")
    @Subcommand("invite")
    fun onCommand(player: Player, offlinePlayer: OfflinePlayer) {
        val friendList = plugin.friendListManager.getFriendList(player.uniqueId)?: return
        if(friendList.friends.containsKey(offlinePlayer.uniqueId)) return player.sendMessage("jestescie juz przyjaculmi.(tak celowo przez u)")
        if(friendList.invites.contains(offlinePlayer.uniqueId)) return player.sendMessage("chuj, cipa, chuj zaprosiles go juz.")
        friendList.invites.add(offlinePlayer.uniqueId)
        player.sendMessage("zaproiles ujka ${offlinePlayer.name} do przyjaciul")
        if (offlinePlayer.isOnline) {
            offlinePlayer.player.sendMessage("chuj ${player.name} cie zaprosil do przyjaciul")
        }
    }

}