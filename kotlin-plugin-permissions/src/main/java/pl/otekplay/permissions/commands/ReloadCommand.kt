package pl.otekplay.permissions.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.permissions.PermissionsPlugin

@CommandAlias("permissions|perm")
@CommandPermission("command.permissions")
class ReloadCommand(plugin: PermissionsPlugin) : PermissionsCommand(plugin) {

    @Subcommand("reload config")
    @CommandPermission("command.permissions.reload.config")
    fun onCommandReloadConfig(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }

    @Subcommand("reload users")
    @CommandPermission("command.permissions.reload.users")
    fun onCommandReloadUsers(sender: CommandSender) {
        plugin.manager.loadGroups()
        sender.sendMessage(plugin.config.messages.youReloadedUsers)
    }

}