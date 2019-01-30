package pl.otekplay.teleporter.commands

import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import pl.otekplay.teleporter.TeleportPlugin
import java.util.*

class TeleportAcceptCommand(val plugin: TeleportPlugin) : PluginCommand() {


    private fun onValidRequest(sender: Player, other: UUID, here: Boolean) {
        val player = Bukkit.getPlayer(other)
                ?: return sender.sendMessage(plugin.config.messages.teleportAcceptLastRequestPlayerOffline)
        if (here) onCommandAcceptHere(sender, player) else onCommandAcceptTo(sender, player)
    }

    @CommandAlias("tpaccept")
    @Description("Komenda zezwala na akceptacje teleportu do kogos.")
    @CommandPermission("command.tpaccept")
    @Syntax("[gracz]")
    @CommandCompletion("@players")
    fun onCommandAcceptTo(sender: Player, @Optional @Flags("other") other: Player?) {
        if (other == null) {
            val lastTo = plugin.teleporter.lastRequestTo.getIfPresent(sender.uniqueId)
            if (lastTo != null) return onValidRequest(sender, lastTo, false)
            val lastHere = plugin.teleporter.lastRequestHere.getIfPresent(sender.uniqueId)
            return if (lastHere != null) onValidRequest(sender, lastHere, true) else sender.sendMessage(plugin.config.messages.teleportAcceptLastRequestInvalid)
        }
        val playerId = sender.uniqueId
        val otherId = other.uniqueId
        val requestId = plugin.teleporter.requestTo.getIfPresent(otherId)
        if (requestId != null && playerId == requestId) {
            plugin.teleporter.teleportPlayerToPlayerWithDelay(other, sender, plugin.config.teleportAskToTime)
            sender.sendMessage(plugin.config.messages.teleportAcceptRequestComplete.rep("%name%", other.name))
            plugin.teleporter.requestTo.invalidate(otherId)
            plugin.teleporter.lastRequestTo.invalidate(otherId)
            return
        }
        sender.sendMessage(plugin.config.messages.teleportAcceptNoRequest.rep("%name%", other.name))
    }

    @CommandAlias("tpahaccept")
    @Description("Komenda zezwala na akceptacje teleportu kogos do siebie.")
    @CommandPermission("command.teleport.tpahaccept")
    @Syntax("<gracz>")
    @CommandCompletion("@players")
    fun onCommandAcceptHere(sender: Player, @Flags("other") other: Player) {
        val playerId = sender.uniqueId
        val otherId = other.uniqueId
        val requestId = plugin.teleporter.requestHere.getIfPresent(otherId)
        if (requestId != null) {
            if (playerId == requestId) {
                plugin.teleporter.teleportPlayerToPlayerWithDelay(other, sender, plugin.config.teleportAskHereTime)
                sender.sendMessage(plugin.config.messages.teleportAcceptRequestComplete.rep("%name%", other.name))
                plugin.teleporter.requestHere.invalidate(otherId)
                plugin.teleporter.lastRequestHere.invalidate(otherId)
                return
            }
        }
        sender.sendMessage(plugin.config.messages.teleportAcceptNoRequest.rep("%name%", sender.name))
    }
}