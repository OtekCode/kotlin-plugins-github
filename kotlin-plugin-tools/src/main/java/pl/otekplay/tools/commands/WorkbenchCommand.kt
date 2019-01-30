package pl.otekplay.tools.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class WorkbenchCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("crafting|workbench")
    @CommandPermission("command.crafting")
    fun onCommand(player: Player) {
        player.openWorkbench(null,true)
    }
}