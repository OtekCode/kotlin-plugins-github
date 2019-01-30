package pl.otekplay.permissions.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import pl.otekplay.permissions.PermissionsPlugin
import pl.otekplay.plugin.rep

@CommandAlias("permissions|perm")
@CommandPermission("command.permissions")
class SuperCommand(plugin: PermissionsPlugin) : PermissionsCommand(plugin) {

    @Subcommand("superuser enable")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    @CommandPermission("command.permissions.superuser.enable")
    fun onCommandEnable(sender: CommandSender, offlinePlayer: OfflinePlayer) {
        val user = plugin.manager.getUser(offlinePlayer.uniqueId) ?: return
        user.superuser = true
        user.updateEntity()
        sender.sendMessage(plugin.config.messages.youEnabledSuperuser.rep("%name%", offlinePlayer.name))
    }

    @Subcommand("superuser disable")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    @CommandPermission("command.permissions.superuser.disable")
    fun onCommandDisable(sender: CommandSender, offlinePlayer: OfflinePlayer) {
        val user = plugin.manager.getUser(offlinePlayer.uniqueId) ?: return
        user.superuser = false
        user.updateEntity()
        sender.sendMessage(plugin.config.messages.youDisabledSuperuser.rep("%name%", offlinePlayer.name))
    }

    @Subcommand("superuser list")
    @CommandPermission("command.permissions.superuser.list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.listSuperuserHeader)
        val list = plugin.manager.users.values.filter { it.superuser }
        sender.sendMessage(list.joinToString(", ") { plugin.config.messages.listSuperuserFormat.rep("%name%", Bukkit.getOfflinePlayer(it.uniqueId).name) })
    }
}