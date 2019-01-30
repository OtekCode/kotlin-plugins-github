package pl.otekplay.mysterybox

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Items

class MysteryBoxListener(private val plugin: MysteryBoxPlugin) : Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) {
        val item = e.itemInHand ?: return
        val box = plugin.manager.boxes.singleOrNull { it.item.toItemStack().isSimilar(item) } ?: return
        val player = e.player
        val random = box.randomItem() ?: return
        val itemStack = random.toItemStack()
        e.blockPlaced.location.world.strikeLightningEffect(e.blockPlaced.location)
        e.isCancelled = true
        Items.removeOneFromHand(player)
        Items.dropItem(e.blockPlaced.location, itemStack)
        Bukkit.broadcastMessage(box.message.rep("%name%", player.name).rep("%boxName%", itemStack.itemMeta.displayName).rep("%amount%", itemStack.amount.toString()))
    }
}