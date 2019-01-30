package pl.otekplay.automessage

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand


class AutoMessageCommand(private val plugin:AutoMessagePlugin) : PluginCommand() {
    @CommandAlias("amreload")
    @CommandPermission("command.automessage.reload")
    fun onCommand(sender: CommandSender) {
        plugin.config = plugin.loadConfig(AutoMessageConfig::class)
        sender.sendMessage(plugin.config.messages.configHasBeenReloaded)
    }
}