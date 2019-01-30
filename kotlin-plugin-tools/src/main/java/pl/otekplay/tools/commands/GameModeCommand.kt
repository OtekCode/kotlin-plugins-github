package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.GameMode
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class GameModeCommand(plugin : ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("gm|gamemode")
    @Syntax("<tryb> [gracz]")
    @CommandCompletion("@players")
    @CommandPermission("command.gamemode")
    fun onCommand(sender: Player, mode :Int, @Optional @Flags("other") other: Player?) {
        val target = other ?: sender
        val gameMode = GameMode.getByValue(mode)
        target.gameMode = gameMode
        sender.sendMessage(plugin.config.messages.youChangedPlayerGamemode.rep("%name%", target.displayName).rep("%gamemode%", gameMode.toString()))
    }
}