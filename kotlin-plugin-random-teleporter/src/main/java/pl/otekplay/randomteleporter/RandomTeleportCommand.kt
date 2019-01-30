package pl.otekplay.randomteleporter

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("randomteleporter|rtp")
class RandomTeleportCommand(private val plugin: RandomTeleportPlugin) : PluginCommand() {


    @Subcommand("random")
    @CommandPermission("command.randomteleporter.random")
    fun onCommandRandom(player: Player) {
        player.teleport(plugin.manager.findSafeTeleportLocation())
    }

    @Subcommand("reload")
    @CommandPermission("command.randomteleporter.reload")
    fun onCommandReload(player: Player) {
        plugin.reloadConfig()
        player.sendMessage(plugin.config.messages.youReloadedConfig)

    }


}