package pl.otekplay.market

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand

class MarketCommand(val plugin: MarketPlugin) : PluginCommand() {


    @CommandAlias("market")
    @CommandPermission("command.market")
    fun onCommand(player: Player) {
        plugin.marketMenu.openMarketMenu(player)
    }
}