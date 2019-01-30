package pl.otekplay.chat.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import pl.otekplay.chat.ChatPlugin
import pl.otekplay.plugin.rep
import java.util.concurrent.TimeUnit

@CommandAlias("chat")
@CommandPermission("command.chat")
class ChatAdminCommand(plugin: ChatPlugin) : ChatCommand(plugin) {

    @Subcommand("lock|cl")
    @CommandPermission("command.chat.lock")
    fun onCommandLock(sender: CommandSender) {
        plugin.config.chatLocked = ! plugin.config.chatLocked
        sender.sendMessage(if (plugin.config.chatLocked) plugin.config.messages.youLockedChat else plugin.config.messages.youUnlockedChat)
    }

    @Subcommand("delay")
    @Syntax("<opoznienie>")
    @CommandPermission("command.chat.delay")
    fun onCommandDelay(sender: CommandSender, delay: Long) {
        plugin.config.delayTimeChat = delay
        sender.sendMessage(plugin.config.messages.youSetDelayChat.rep("%time%", "${TimeUnit.MILLISECONDS.toSeconds(delay)}"))
    }

    private val cleanMessage = arrayOfNulls<String>(100).also { it.fill("") }

    @Subcommand("clear|cc")
    @CommandPermission("command.chat.clear")
    fun onCommandClear(sender: CommandSender) {
        Bukkit.getOnlinePlayers().forEach { it.sendMessage(cleanMessage) }
        Bukkit.broadcastMessage(plugin.config.messages.chatHasBeenCleared.rep("%admin%", sender.name))
    }

    @Subcommand("reload")
    @CommandPermission("command.chat.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}