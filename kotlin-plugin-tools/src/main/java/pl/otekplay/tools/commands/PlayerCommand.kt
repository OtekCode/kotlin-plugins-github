package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

class PlayerCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("gracz")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    @CommandPermission("command.player")
    fun onCommand(sender: Player, @Flags("other") other: OfflinePlayer) {
        val messageList = arrayListOf(*config.messages.playerInfo.toTypedArray())
        messageList.replaceAll {it.rep("%name%", other.name)
                .rep("%online%", if (other.isOnline) config.messages.tagYes else config.messages.tagNo)
                .rep("%lastplayed%", Dates.formatData(other.lastPlayed))  }
        sender.sendMessage(messageList.toTypedArray())
    }
}