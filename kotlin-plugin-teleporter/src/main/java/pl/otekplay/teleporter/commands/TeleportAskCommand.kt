package pl.otekplay.teleporter.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import pl.otekplay.teleporter.TeleportPlugin

class TeleportAskCommand(private val plugin: TeleportPlugin) : PluginCommand() {

    @CommandAlias("tpa")
    @Description("Komenda pozwala na teleportacje do gracza za pozwoleniem")
    @CommandPermission("command.tpa")
    @CommandCompletion("@players")
    @Syntax("<gracz>")
    fun onCommandToRequest(sender: Player, @Flags("other") other: Player) {
        val playerId = sender.uniqueId
        val otherId = other.uniqueId
        if(playerId == otherId) return sender.sendMessage(plugin.config.messages.cantTeleportToYourself)
        val requestId = plugin.teleporter.requestTo.getIfPresent(playerId)
        if (requestId != null && requestId == otherId) return sender.sendMessage(plugin.config.messages.teleportAskAlreadyAsked.rep("%name%", other.name))
        plugin.teleporter.requestTo.put(playerId, otherId)
        plugin.teleporter.lastRequestTo.put(otherId, playerId)
        sender.sendMessage(plugin.config.messages.teleportAskToInfoSender.rep("%name%", other.name))
        other.sendMessage(plugin.config.messages.teleportAskToInfoOther.rep("%name%", sender.name))
    }

    @CommandAlias("tpahere")
    @Description("Komenda pozwala na przywolanie gracza za pozwoleniem")
    @CommandPermission("command.teleport.tpahere")
    @CommandCompletion("@players")
    @Syntax("<gracz>")
    fun onCommandHereRequest(sender: Player, @Flags("other") other: Player) {
        val playerId = sender.uniqueId
        val otherId = other.uniqueId
        if(playerId == otherId) return sender.sendMessage(plugin.config.messages.cantTeleportToYourself)
        val requestId = plugin.teleporter.requestHere.getIfPresent(sender.uniqueId)
        if (requestId != null) {
            if (requestId == otherId) {
                sender.sendMessage(plugin.config.messages.teleportAskAlreadyAsked.rep("%name%", other.name))
                return
            }
        }
        plugin.teleporter.requestHere.put(playerId, otherId)
        plugin.teleporter.lastRequestHere.put(otherId, playerId)
        sender.sendMessage(plugin.config.messages.teleportAskHereInfoSender.rep("%name%", other.name))
        other.sendMessage(plugin.config.messages.teleportAskHereInfoOther.rep("%name%", sender.name))
    }


}