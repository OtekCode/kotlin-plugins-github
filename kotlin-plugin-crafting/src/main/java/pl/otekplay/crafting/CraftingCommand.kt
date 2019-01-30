package pl.otekplay.crafting

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand


class CraftingCommand(private val plugin: CraftingPlugin) : PluginCommand() {

    @CommandAlias("craftings")
    @CommandPermission("command.craftings")
    fun onCommandCrafting(player: Player) {
        plugin.showCraftingMenu(player)
    }

    @CommandAlias("craftingsreload")
    @CommandPermission("command.craftingsreload")
    fun onCommandCraftingReload(player: Player) {
        plugin.reloadConfig()
        player.sendMessage(plugin.config.messages.youReloadedConfig)
    }

}