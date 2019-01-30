package pl.otekplay.tools.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

@CommandAlias("tools")
class ReloadCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @Subcommand("reload")
    @CommandPermission("command.tools.reload")
    fun onCommand(sender: Player) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}