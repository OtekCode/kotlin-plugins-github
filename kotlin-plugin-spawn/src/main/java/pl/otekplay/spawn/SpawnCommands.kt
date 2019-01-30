package pl.otekplay.spawn

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.teleporter.TeleportAPI

class SpawnCommands(val plugin: SpawnPlugin) : PluginCommand() {


    @CommandAlias("spawn")
    @CommandPermission("command.spawn")
    fun onCommandSpawn(player: Player) {
        TeleportAPI.teleportPlayerToLocationWithDelay(player, plugin.spawnLocation, plugin.config.spawnTeleportDelay, true)
    }

    @CommandAlias("setspawn")
    @CommandPermission("command.setspawn")
    fun onCommandSetSpawn(player: Player) {
        plugin.spawnLocation = player.location
        player.sendMessage(plugin.config.messages.setSpawnComplete)
    }

    @CommandAlias("spawnreload")
    @CommandPermission("command.spawnreload")
    fun onCommandSpawnReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }

}