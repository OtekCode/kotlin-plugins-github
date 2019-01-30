package pl.otekplay.menu

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import pl.otekplay.plugin.rep

class CustomMenuListener(private val plugin: CustomMenuPlugin) : Listener {


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onPlayerCommandPreprocess(e: PlayerCommandPreprocessEvent) {
        val message = e.message.rep("/", "")
        val arg = if (message.contains(" ")) message.split(" ")[0].rep(" ", "") else message
        val menu = plugin.manager.getMenu(arg) ?: return
        menu.open(e.player)
        e.isCancelled = true
    }
}