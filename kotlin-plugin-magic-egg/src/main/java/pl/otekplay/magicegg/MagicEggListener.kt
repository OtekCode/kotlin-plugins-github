package pl.otekplay.magicegg

import org.bukkit.Effect
import org.bukkit.entity.Egg
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.plugin.util.Items

class MagicEggListener(private val plugin: MagicEggPlugin) : Listener {


    @EventHandler
    fun onPlayerEggThrow(e: PlayerEggThrowEvent) {
        val egg = e.egg
        if (egg.customName == null) return
        val configEgg = plugin.manager.getMagicEgg(egg.customName) ?: return
        val item = configEgg.randomItem()?.toItemStack() ?: return
        Items.dropItem(egg.location, item)
        e.isHatching = false
        egg.world.strikeLightningEffect(egg.location)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onProjectileLaunch(e: ProjectileLaunchEvent) {
        if(e.entity.type !== EntityType.EGG) return
        val egg = e.entity as Egg
        if(egg.shooter !is Player) return
        val player = egg.shooter as Player
        val hand = player.itemInHand
        val config = plugin.manager.getMagicEggByItem(hand) ?: return
        e.entity.velocity = e.entity.velocity.multiply(config.speed)
        egg.customName = config.name
        plugin.taskTimerSync(object : BukkitRunnable() {
            override fun run() {
                if(egg.isDead) cancel()
                egg.location.world.spigot().playEffect(egg.location,Effect.COLOURED_DUST,1,2,0.3F,0.3F,0.3F,0.5F,20,30)
            }

        },1)

    }
}