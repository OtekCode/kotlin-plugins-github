package pl.otekplay.inventorylimits

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand

class InventoryLimitCommand(private val plugin: InventoryLimitPlugin) : PluginCommand() {


    @CommandAlias("depozyt|schowek|deposit")
    @CommandPermission("command.depozyt")
    fun onCommandDeposit(player: Player) {
        plugin.manager.openDepositMenu(player)
    }

    @CommandAlias("depozytreload|schowekreload|depositreload")
    @CommandPermission("command.depozytreload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}