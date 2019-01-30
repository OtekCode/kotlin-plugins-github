package pl.otekplay.guild.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

class PlayerListeners(plugin: GuildPlugin) : GuildListener(plugin) {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        if (block.type != Material.DRAGON_EGG) return
        e.isCancelled = true
        val guild = manager.getGuildByLocation(block.location) ?: return
        if (guild.getMember(player.uniqueId) != null) return
        val playerGuild = manager.getGuildByMember(player.uniqueId) ?: return
        if (manager.hasAlly(playerGuild, guild)) return
        if (manager.isGuildProtected(guild)) player.sendMessage(plugin.config.messages.guildIsProtected.rep("%tag%", guild.tag)) else manager.destroyEggGuild(player, playerGuild, guild)
    }
}