package pl.otekplay.tools.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class EnderchestCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("enderchest")
    @CommandPermission("command.enderchest")
    fun onCommand(player: Player) {
        player.openInventory(player.enderChest)
    }

}