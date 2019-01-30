package pl.otekplay.guild.listeners

import org.bukkit.event.Listener
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.guild.holders.GuildManager

abstract class GuildListener(val plugin: GuildPlugin) :Listener{
    val manager: GuildManager = plugin.manager


}