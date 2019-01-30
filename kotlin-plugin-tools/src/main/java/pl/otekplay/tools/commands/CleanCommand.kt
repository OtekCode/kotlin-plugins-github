package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class CleanCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("clearinv|clean|clear")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.clearinv")
    fun onCommandClearInv(sender: Player, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        target.inventory.clear()
        sender.sendMessage(config.messages.youClearedPlayerInventory.rep("%name%",target.name))
    }

    @CommandAlias("clearec")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.cleanec")
    fun onCommandClearEc(sender: Player, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        target.inventory.clear()
        sender.sendMessage(config.messages.youClearedPlayerEnderchest.rep("%name%",target.name))
    }
}