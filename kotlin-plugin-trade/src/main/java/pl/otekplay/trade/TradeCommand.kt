package pl.otekplay.trade

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("trade")
@CommandPermission("command.trade")
class TradeCommand(private val plugin: TradePlugin) : PluginCommand() {


    @Subcommand("reload")
    @CommandPermission("command.trade.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}