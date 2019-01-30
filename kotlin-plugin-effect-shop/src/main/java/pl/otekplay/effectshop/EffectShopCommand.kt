package pl.otekplay.effectshop

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand

class EffectShopCommand(private val plugin: EffectShopPlugin) : PluginCommand() {

    @CommandAlias("efekty|effect")
    @CommandPermission("command.efekty")
    fun onCommand(player: Player) {
        plugin.menu.openMenu(player)
    }
}