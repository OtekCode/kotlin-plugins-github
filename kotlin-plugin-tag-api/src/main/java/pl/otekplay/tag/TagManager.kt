package pl.otekplay.tag

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam
import net.minecraft.server.v1_8_R3.Scoreboard
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.plugin.util.Reflections

class TagManager(val plugin: TagPlugin) {

    private val board = Scoreboard()

    fun createPlayer(player: Player) {
        plugin.logger.info("Creating scoreboard for player ${player.name}")
        val name = player.name
        val team = board.createTeam(name).apply {
            displayName = ""
            prefix = ""
            suffix = ""
        }
        board.addPlayerToTeam(name, name)
        val packet = PacketPlayOutScoreboardTeam(team, 0)
        Bukkit.getOnlinePlayers()
                .forEach { Reflections.sendPacket(it, packet) }
        Bukkit.getOnlinePlayers()
                .minus(player)
                .mapNotNull { board.getPlayerTeam(it.name) }
                .map { PacketPlayOutScoreboardTeam(it, 0) }
                .forEach { Reflections.sendPacket(player, it) }
        plugin.logger.info("Scoreboard for player $name has been created.")
    }




    fun updatePlayer(player: Player) {
        plugin.logger.info("Scoreboard player ${player.name} updating...")
        val team = board.getPlayerTeam(player.name) ?: return
        Bukkit.getOnlinePlayers()
                .forEach {
                    val event = PlayerTagEvent(player, it, true, "", "")
                    Bukkit.getPluginManager().callEvent(event)
                    if(event.canSee){
                        team.nameTagVisibility = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS
                    }else {
                        team.nameTagVisibility = ScoreboardTeamBase.EnumNameTagVisibility.NEVER
                    }
                    if (event.prefix.length > 16) {
                        event.prefix = event.prefix.substring(0, 15)
                    }
                    if (event.suffix.length > 16) {
                        event.suffix = event.suffix.substring(0, 15)
                    }
                    team.prefix = event.prefix
                    team.suffix = event.suffix
                    Reflections.sendPacket(it, PacketPlayOutScoreboardTeam(team, 2))
                }
        plugin.logger.info("Scoreboard player ${player.name} has been updated.")
    }

    fun removeBoard(player: Player) {
        plugin.logger.info("Removing scoreboard player ${player.name}")
        val name = player.name
        val team = board.getPlayerTeam(name) ?: return
        board.removePlayerFromTeam(name, team)
        val packet = PacketPlayOutScoreboardTeam(team, 1)
        Bukkit.getOnlinePlayers().forEach {
            Reflections.sendPacket(it, packet)
        }
        Bukkit.getOnlinePlayers()
                .minus(player)
                .mapNotNull { PacketPlayOutScoreboardTeam(board.getPlayerTeam(it.name), 1) }
                .forEach { Reflections.sendPacket(player, it) }
        board.removeTeam(team)
        plugin.logger.info("Scoreboard $name has been removed.")
    }

}