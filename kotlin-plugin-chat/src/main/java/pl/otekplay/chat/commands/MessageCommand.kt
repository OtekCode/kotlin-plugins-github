package pl.otekplay.chat.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.otekplay.chat.ChatPlugin
import pl.otekplay.plugin.rep
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap

class MessageCommand(plugin: ChatPlugin) : ChatCommand(plugin) {

    private val lastMessages = HashMap<UUID, UUID>()

    @CommandAlias("msg|pw|m|message")
    @CommandPermission("command.msg")
    @Syntax("<gracz> <wiadomosc>")
    @CommandCompletion("@players")
    fun onCommandMessage(sender: Player, @Flags("other") other: Player, message: String) {
        if (sender == other) return sender.sendMessage(plugin.config.messages.youSendYourselfMessage)
        send(sender, other, message)
    }

    @CommandAlias("reply|r|rp|rep")
    @CommandPermission("command.msg")
    @Syntax("<wiadomosc>")
    fun onCommandReply(sender: Player, message: String) {
        if(!lastMessages.contains(sender.uniqueId)) return sender.sendMessage(plugin.config.messages.youGotNoReplyPlayeer)
        val receiver = Bukkit.getPlayer(lastMessages[sender.uniqueId]) ?: return sender.sendMessage(plugin.config.messages.youGotNoReplyPlayeer)
        send(sender, receiver, message)
    }


    private fun send(sender: Player, receiver: Player, message: String) {
        val receiverMessage = plugin.config.formatMessageReceive
                .rep("%sender%", sender.displayName)
                .rep("%receiver%", receiver.displayName)
                .rep("%message%", message)
        val senderMessage = plugin.config.formatMessageSend
                .rep("%sender%", sender.displayName)
                .rep("%receiver%", receiver.displayName)
                .rep("%message%", message)
        sender.sendMessage(senderMessage)
        receiver.sendMessage(receiverMessage)
        lastMessages[receiver.uniqueId] = sender.uniqueId
    }
}