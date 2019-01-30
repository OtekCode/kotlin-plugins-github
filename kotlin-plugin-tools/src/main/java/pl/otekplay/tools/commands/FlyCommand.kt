package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class FlyCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("fly")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.fly")
    fun onCommand(sender: Player, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        val enable = !target.isFlying
        target.allowFlight = enable
        target.isFlying = enable
        sender.sendMessage(if (target.isFlying) config.messages.flyHasBeenEnabled else config.messages.flyHasBeenDisabled)
    }
}