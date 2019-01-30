package pl.otekplay.farmer

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import pl.otekplay.plugin.rep

class FarmerListener(private val plugin: FarmerPlugin):Listener {


    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) {
        val item = e.itemInHand ?: return
        val farmer = plugin.manager.farmers.singleOrNull { it.recipe.result.toItemStack().isSimilar(item) } ?: return
        plugin.taskLaterSync(FarmerTask(plugin,farmer,e.blockPlaced.location,0),farmer.buildTime)
        e.player.sendMessage(plugin.config.messages.farmerPlaced.rep("%name%",farmer.name))
    }
}