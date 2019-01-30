package pl.otekplay.tools.system.vanish

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.tools.ToolsManager
import pl.otekplay.tools.ToolsPlugin
import java.util.*

class VanishManager(plugin: ToolsPlugin) : ToolsManager(plugin) {
    override fun registerVariables() {

    }

    private val vanishedPlayers = hashSetOf<UUID>()

    override fun registerCommands() {
        plugin.registerCommand(VanishCommand(this))
    }

    override fun registerListeners() {
        plugin.registerListener(VanishListener(this))
    }

    fun hasVanish(uniqueId: UUID) = vanishedPlayers.contains(uniqueId)

    fun changeStateVanish(uniqueId: UUID) =
            if (hasVanish(uniqueId))
                vanishedPlayers.remove(uniqueId)
            else
                vanishedPlayers.add(uniqueId)

    fun refreshOthers(player: Player) = Bukkit
            .getOnlinePlayers()
            .minus(player)
            .filter { hasVanish(it.uniqueId) }
            .forEach { player.hidePlayer(it) }

    fun hidePlayer(player: Player) = Bukkit
            .getOnlinePlayers()
            .minus(player)
            .filter { !it.hasPermission(config.permissionVanishBypass) }
            .forEach { it.hidePlayer(player) }

    fun showPlayer(player: Player) = Bukkit
            .getOnlinePlayers()
            .minus(player)
            .forEach { it.showPlayer(player) }

}