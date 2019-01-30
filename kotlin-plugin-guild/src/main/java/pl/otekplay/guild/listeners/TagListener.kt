package pl.otekplay.guild.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.tag.PlayerTagEvent

class TagListener(plugin: GuildPlugin) : GuildListener(plugin) {

    @EventHandler(priority = EventPriority.NORMAL)
    fun onPlayerTag(e: PlayerTagEvent) {
        e.prefix = e.prefix + plugin.manager.getGuildTagFor(e.player,e.requester)
    }
}