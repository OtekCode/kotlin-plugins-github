package pl.otekplay.backup

import co.aikar.commands.annotation.*
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep

@CommandAlias("backup")
@CommandPermission("command.backup")
class BackupCommands(private val plugin: BackupPlugin) : PluginCommand() {
    private val config = plugin.config
    private val manager = plugin.manager

    @Subcommand("show")
    @CommandCompletion("@players")
    @CommandPermission("command.backup.show")
    @Syntax("<gracz>")
    fun onCommandShow(player: Player, other: OfflinePlayer) {
        val playerBackup = manager.getBackup(other.uniqueId) ?: return
        plugin.menu.openMenuWithListBackups(player, playerBackup)
    }

    @Subcommand("save")
    @CommandCompletion("@players")
    @CommandPermission("command.backup.save")
    @Syntax("<gracz>")
    fun onCommandSave(player: Player, @Flags("other") other: Player) {
        plugin.manager.saveBackup(other, BackupType.COMMAND)
        player.sendMessage(config.messages.youSaveBackup.rep("%name%", other.name))
    }

    @Subcommand("reload config")
    @CommandCompletion("@players")
    @CommandPermission("command.backup.reload.config")
    fun onCommandReloadConfig(player: Player) {
        plugin.reloadConfig()
        player.sendMessage(plugin.config.messages.youReloadedConfig)
    }

    @Subcommand("reload users")
    @CommandCompletion("@players")
    @CommandPermission("command.backup.reload.users")
    fun onCommandReloadUsers(player: Player) {
        plugin.manager.loadBackups()
        player.sendMessage(plugin.config.messages.youReloadedBackups)
    }
}