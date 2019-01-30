package pl.otekplay.tag

import org.bukkit.Bukkit
import org.bukkit.entity.Player

object TagAPI {
    internal lateinit var plugin: TagPlugin

    fun refresh() = refresh(Bukkit.getOnlinePlayers())

    fun refresh(player: Player) = plugin.manager.updatePlayer(player)

    private fun refresh(players: Collection<Player>) = players.forEach { refresh(it) }

    fun refresh(players: Array<Player>) = players.forEach { refresh(it) }

}