package pl.otekplay.guild.tasks

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

class GuildExpireTask(val plugin: GuildPlugin) : BukkitRunnable() {
    override fun run() {
        val manager = plugin.manager
        manager.guilds
                .filter { manager.isGuildExpired(it) }
                .forEach {
                    Bukkit.broadcastMessage(plugin.config.messages.guildHasBeenExpired
                            .rep("%tag%", it.tag)
                            .rep("%x%", it.center.blockX.toString())
                            .rep("%z%", it.center.blockZ.toString()))
                    manager.removeGuild(it)
                }
    }

}