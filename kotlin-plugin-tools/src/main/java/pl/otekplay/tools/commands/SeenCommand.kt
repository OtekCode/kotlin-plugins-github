package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class SeenCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("aktywnosc|seen")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.aktywnosc")
    fun onCommand(sender: Player, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        sender.sendMessage(plugin.config.messages.lastTimeSeen.rep("%name%",target.name).rep("%date%", Dates.formatData(target.lastPlayed)))
    }
}