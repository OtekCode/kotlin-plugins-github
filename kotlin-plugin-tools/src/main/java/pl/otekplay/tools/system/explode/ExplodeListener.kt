package pl.otekplay.tools.system.explode

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityExplodeEvent
import pl.otekplay.plugin.util.Locations
import pl.otekplay.plugin.util.Randoms

class ExplodeListener(private val manager: ExplodeManager) : Listener {


    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockExplodeLowPriority(e: BlockExplodeEvent) {
        if (manager.isExplodeBlocked()) e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityExplode(e: EntityExplodeEvent) {
        if (manager.isExplodeBlocked()) e.isCancelled = true
    }


}