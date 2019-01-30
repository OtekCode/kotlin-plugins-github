package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.permissions.PermissionsAPI
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin


class WhoisCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("whois")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    @CommandPermission("command.whois")
    fun onCommand(sender: Player, @Flags("other") other: OfflinePlayer) {
        val messageList = arrayListOf(*config.messages.whoisInfo.toTypedArray())
        messageList.replaceAll {
            it.rep("%name%", other.name)
                    .rep("%uniqueid%", other.uniqueId.toString())
                    .rep("%whitelist%", if (other.isWhitelisted) config.messages.tagYes else config.messages.tagNo)
                    .rep("%online%", if (other.isOnline) config.messages.tagYes else config.messages.tagNo)
                    .rep("%vanish%", if (plugin.vanishManager.hasVanish(other.uniqueId)) config.messages.tagYes else config.messages.tagNo)
                    .rep("%firstplayed%", other.firstPlayed.toString())
                    .rep("%group%", PermissionsAPI.getUser(other.uniqueId)?.group ?: config.messages.tagEmpty)
                    .rep("%lastplayed%", other.lastPlayed.toString())
        }
        sender.sendMessage(messageList.toTypedArray())
    }
}