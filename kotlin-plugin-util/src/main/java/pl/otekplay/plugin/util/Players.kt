package pl.otekplay.plugin.util

import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import pl.otekplay.plugin.rep

object Players {

    private fun getPlayerStringColored(player: OfflinePlayer) = "${if (player.isOnline) ChatColor.GREEN else ChatColor.RED}${player.name}"

    private fun getPlayersStringColored(players: Array<OfflinePlayer>): String {
        if (players.isEmpty()) {
            return "[]"
        }
        val builder = StringBuilder("[")
        players.forEach {
            builder.append(getPlayerStringColored(it))
            builder.append(", ")
        }
        builder.append("]")
        return builder.toString().rep(", ]", "]")
    }

    fun getPlayersStringColored(players: Collection<OfflinePlayer>) = getPlayersStringColored(players.toTypedArray())

}