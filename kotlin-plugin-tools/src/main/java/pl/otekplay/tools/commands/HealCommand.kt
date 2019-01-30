package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class HealCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {


    @CommandAlias("heal|h")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.heal")
    fun onCommand(sender: Player, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        target.health = 20.0
        sender.sendMessage(plugin.config.messages.youHealedPlayer.rep("%name%", target.name))
    }
}