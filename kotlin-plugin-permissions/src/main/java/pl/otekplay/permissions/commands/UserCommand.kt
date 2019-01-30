package pl.otekplay.permissions.commands

import co.aikar.commands.annotation.*
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import pl.otekplay.permissions.PermissionsPlugin
import pl.otekplay.plugin.rep

@CommandAlias("permissions|perm")
class UserCommand(plugin: PermissionsPlugin) : PermissionsCommand(plugin) {

    @Subcommand("user info")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    @CommandPermission("command.permissions.user")
    fun onCommandInfo(sender: CommandSender, @Flags("other") other: OfflinePlayer) {
        val user = plugin.manager.getUser(other.uniqueId) ?: return
        plugin.config.messages.userInfoFormat.forEach {
            sender.sendMessage(it
                    .rep("%name%", other.name)
                    .rep("%group%", user.group)
                    .rep("%superuser%", if (user.superuser) plugin.config.messages.superuserEnabled else plugin.config.messages.superuserDisabled))
        }
    }

    @Subcommand("user group set")
    @Syntax("<gracz> <grupa>")
    @CommandCompletion("@players")
    @CommandPermission("command.permissions.user")
    fun onCommandSet(sender: CommandSender, @Flags("other") other: OfflinePlayer, groupName: String) {
        val user = plugin.manager.getUser(other.uniqueId) ?: return
        val group = plugin.manager.getGroup(groupName) ?: return sender.sendMessage(plugin.config.messages.groupNotFound)
        user.group = group.name
        user.updateEntity()
        sender.sendMessage(plugin.config.messages.youChangedPlayerGroup.rep("%group%", user.group).rep("%name%", other.name))
    }

    @Subcommand("user perm add")
    @Syntax("<gracz> <permisja>")
    @CommandCompletion("@players")
    @CommandPermission("command.permissions.user.perm.add")
    fun onCommandUserPermAdd(sender: CommandSender, @Flags("other") other: OfflinePlayer, perm: String) {
        val user = plugin.manager.getUser(other.uniqueId) ?: return
        if(user.permissions.contains(perm)) return sender.sendMessage(plugin.config.messages.playerAlreadyHaveThisPerm.rep("%name%",other.name).rep("%perm%",perm))
        user.permissions.remove(perm)
        user.updateEntity()
        sender.sendMessage(plugin.config.messages.youAddedPermToUser.rep("%name%",other.name).rep("%perm%",perm))

    }

    @Subcommand("user perm remove")
    @Syntax("<gracz> <permisja>")
    @CommandCompletion("@players")
    @CommandPermission("command.permissions.user.perm.remove")
    fun onCommandUserPermRemove(sender: CommandSender, @Flags("other") other: OfflinePlayer, perm: String) {
        val user = plugin.manager.getUser(other.uniqueId) ?: return
        if(!user.permissions.contains(perm)) return sender.sendMessage(plugin.config.messages.playerDontHaveThisPerm.rep("%name%",other.name).rep("%perm%",perm))
        user.permissions.remove(perm)
        user.updateEntity()
        sender.sendMessage(plugin.config.messages.youRemovePermFromUser.rep("%name%",other.name).rep("%perm%",perm))
    }


}