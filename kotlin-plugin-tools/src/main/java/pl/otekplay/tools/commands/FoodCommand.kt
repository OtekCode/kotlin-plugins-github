package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class FoodCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {


    @CommandAlias("food|f")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.food")
    fun onCommand(sender: Player, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        target.foodLevel = 20
        sender.sendMessage(plugin.config.messages.youFeededPlayer.rep("%name%",target.name))
    }
}