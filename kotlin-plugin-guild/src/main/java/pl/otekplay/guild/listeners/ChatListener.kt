package pl.otekplay.guild.listeners

import org.bukkit.event.EventHandler
import pl.otekplay.chat.ChatFormatEvent
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

class ChatListener(plugin: GuildPlugin) : GuildListener(plugin) {
    @EventHandler
    fun onChat(event: ChatFormatEvent) {
        if (!event.format.contains(plugin.config.guildChatFormatReplace)) return

        val tag = manager.getGuildTagFor(event.sender, event.receiver)

        event.format = event.format.rep(plugin.config.guildChatFormatReplace, tag)
    }
}