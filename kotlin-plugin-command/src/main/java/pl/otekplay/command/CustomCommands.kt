package pl.otekplay.command

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep


@CommandAlias("customcommand")
@CommandPermission("command.customcommand")
class CustomCommands(private val plugin: CustomCommandPlugin) : PluginCommand() {

    @Subcommand("reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.commandsReload)
    }

    @Subcommand("list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.commandList)
        plugin.manager.commands.forEach {
            val builder = StringBuilder("")
            it.aliases.forEach {
                builder.append(it)
                builder.append(", ")
            }
            val toString = builder.toString()
            sender.sendMessage(
                    plugin.config.messages.commandListFormat
                            .rep("%name%", it.name)
                            .rep("%aliases%", if (toString.isEmpty()) toString else toString.substring(0, toString.length - 2)))
        }
    }

    @Subcommand("aliases")
    fun onCommandAliases(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.commandList)
        plugin.config.commandAliases.forEach {
            sender.sendMessage(
                    plugin.config.messages.commandAliasFormat
                            .rep("%alias%", it.key)
                            .rep("%full%", it.value))
        }
    }


}