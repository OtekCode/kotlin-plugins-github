package pl.otekplay.tag

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("tag")
@CommandPermission("command.tag")
class TagCommand(val plugin: TagPlugin) : PluginCommand() {


    @Subcommand("reload")
    @CommandPermission("command.tag.reload")
    fun onCommand(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}