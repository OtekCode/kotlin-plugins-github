package pl.otekplay.randomteleporter

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigEnchantedItem

class RandomTeleportConfig:PluginConfiguration{
    val messages = RandomTeleportMessages()
    val randomTeleportMin = -1000
    val randomTeleportMax = 1000
    val safeBlocks = arrayListOf(
            Material.GRASS.id,
            Material.SAND.id,
            Material.DIRT.id
    )
    val randomTeleportButtonCooldown = 10000L
    val groupTeleportRangeDistance = 5
    val singleButtonBlocksId = arrayListOf(
            Material.SPONGE.id
    )
    val groupButtonBlocksId = arrayListOf(
            Material.OBSIDIAN.id
    )

    val itemsAfterTeleport =
            arrayListOf(ConfigEnchantedItem("SuperItem",Material.WOOD_PICKAXE.id))
    val randomTeleportOnFirstJoin = true

}