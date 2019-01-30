package pl.otekplay.kit

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand

class KitCommand(private val plugin: KitPlugin) : PluginCommand() {


    @CommandAlias("kit|kits")
    @CommandPermission("command.kit")
    fun onCommandKits(player: Player) {
        plugin.menu.openKitMenu(player)
    }

    @CommandAlias("kitreload|kitsreload")
    @CommandPermission("command.kitreload")
    fun onCommandKitsReload(player: Player) {
        plugin.loadConfig()
        plugin.manager.loadKits()
        plugin.manager.cleanInvalidKits()
        player.sendMessage(plugin.config.messages.youReloadedKits)
    }
}