package pl.otekplay.generator

import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.rep

class GeneratorListener(private val plugin: GeneratorPlugin) : Listener {
    private val manager = plugin.manager

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockPistonExtend(e: BlockPistonExtendEvent) {
        val bf = e.direction
        val block = e.block
        val location = block.location
        if (manager.getGeneratorByLocation(location) != null) {
            e.isCancelled = true
            return
        }

        for (i in 1..15) {
            if (manager.getGeneratorByLocation(block.getRelative(bf, i).location) == null) continue
            e.isCancelled = true
            break
        }
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockExplode(e: BlockExplodeEvent) {
        removeExplodedGenerators(e.blockList())
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockExplode(e: EntityExplodeEvent) {
        removeExplodedGenerators(e.blockList())
    }

    private fun removeExplodedGenerators(blocks: List<Block>) {
        for (block in blocks) {
            val location = block.location.clone().subtract(0.0, 1.0, 0.0)
            val generator = manager.getGeneratorByLocation(location) ?: continue
            manager.removeGenerator(generator.uniqueId)
        }
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) {
        val block = e.block
        val player = e.player
        val generator = manager.getGeneratorByLocation(block.location)
        if (generator != null) {
            manager.removeGenerator(generator.uniqueId)
            player.sendMessage(plugin.config.messages.youBreakGenerator)
            e.isCancelled = true
            val configGenerator = manager.getConfigFromGenerator(generator) ?: return
            Items.addItem(player, configGenerator.recipe.result.toItemStack())
            player.sendMessage(plugin.config.messages.youGotGeneratorBack)

        }
        manager.destroyGenerator(block.world.getBlockAt(block.x, block.y - 1, block.z).location)
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) {
        val config = manager.getConfigWithItem(e.itemInHand) ?: return
        manager.createGenerator(e.blockPlaced.location, config)
        e.player.sendMessage(plugin.config.messages.youPlacedGenerator.rep("%name%", config.name))
    }
}