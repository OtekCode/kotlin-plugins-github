package pl.otekplay.itemshop

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand


class ItemShopCommand(val plugin:ItemShopPlugin) : PluginCommand() {

    @CommandAlias("isreload")
    @CommandPermission("command.isreload")
    fun onCommand(player: Player) {
        plugin.reloadServer()
        player.sendMessage(plugin.config.messages.youReloadedServer)
    }
}