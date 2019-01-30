package pl.otekplay.guild.listeners

import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import pl.otekplay.guild.GuildPlugin

class FightListeners(plugin: GuildPlugin) : GuildListener(plugin) {


    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val player = e.entity as? Player ?: return
        val bowAttack: Boolean
        val attacker = if (e.damager is Arrow) {
            val arrow = e.damager as Arrow
            if (arrow.shooter is Player) {
                bowAttack = true
                arrow.shooter as Player
            } else return
        } else if (e.damager is Player) {
            bowAttack = false
            e.damager as Player
        } else return
        val playerGuild = manager.getGuildByMember(player.uniqueId) ?: return
        val attackerGuild = manager.getGuildByMember(attacker.uniqueId) ?: return
        if (playerGuild != attackerGuild) return
        val options = playerGuild.options
        if (bowAttack) {
            if (options.bow) return
            attacker.sendMessage(plugin.config.messages.pvPBowInGuildIsDisabled)
            e.isCancelled = true
        } else {
            if (options.pvp) return
            attacker.sendMessage(plugin.config.messages.pvPInGuildIsDisabled)
            e.isCancelled = true
        }
    }

}