package pl.otekplay.command

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import pl.otekplay.plugin.rep

class CustomCommandListener(private val plugin: CustomCommandPlugin) : Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPreCommandReplaceAliases(e: PlayerCommandPreprocessEvent) {
        val alias = plugin.config.commandAliases.keys.singleOrNull { e.message.substring(1,e.message.length).startsWith(it) } ?: return
        val replacer = plugin.config.commandAliases[alias] ?: return
        if(replacer.contains(alias)) return
        e.message = e.message.rep(alias, replacer)
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun onPreCommandCustomCommands(e: PlayerCommandPreprocessEvent) {
        val message = e.message.rep("/", "")
        val arg = if (message.contains(" ")) message.split(" ")[0].rep(" ", "") else message
        val command = plugin.manager.getCommand(arg) ?: return
        e.player.sendMessage(command.messages.toTypedArray())
        e.isCancelled = true
    }
}