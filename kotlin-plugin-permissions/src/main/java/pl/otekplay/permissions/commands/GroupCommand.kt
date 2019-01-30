package pl.otekplay.permissions.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.command.CommandSender
import pl.otekplay.permissions.PermissionsPlugin
import pl.otekplay.plugin.rep

@CommandAlias("permissions|perm")
@CommandPermission("command.permissions")
class GroupCommand(plugin: PermissionsPlugin) : PermissionsCommand(plugin) {


    @Subcommand("group list")
    @CommandPermission("command.permissions.group")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.groupListHeader)
        plugin.manager.groups.values.forEach {
            sender.sendMessage(plugin.config.messages.groupListFormat
                    .rep("%group%", it.name)
                    .rep("%permissions%", "${it.permissions.size}"))
        }
    }

    @Subcommand("group info")
    @Syntax("<grupa>")
    @CommandPermission("command.permissions.group")
    fun onCommandInfo(sender: CommandSender, name: String) {
        val group = plugin.manager.getGroup(name) ?: return sender.sendMessage(plugin.config.messages.groupNotFound)
        plugin.config.messages.groupInfoFormat.forEach {
            sender.sendMessage(it
                    .rep("%group%", group.name)
                    .rep("%permissions%", "${group.permissions.size}"))
        }
    }
}