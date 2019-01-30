package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class OpenCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("openinv|oi")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.openinv")
    fun onCommandClearInv(sender: Player, @Optional @Flags("other") other: Player?) {
        sender.openInventory(other?.inventory ?: sender.inventory)
    }

    @CommandAlias("openec|oe")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.openec")
    fun onCommandClearEc(sender: Player, @Optional @Flags("other") other: Player?) {
        sender.openInventory(other?.enderChest ?: sender.enderChest)
    }
}