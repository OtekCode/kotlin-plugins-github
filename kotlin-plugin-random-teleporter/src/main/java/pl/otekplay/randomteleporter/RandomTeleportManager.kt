package pl.otekplay.randomteleporter

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import pl.otekplay.guild.GuildAPI
import pl.otekplay.plugin.util.Locations
import pl.otekplay.plugin.util.Randoms
import pl.otekplay.spawn.SpawnAPI

class RandomTeleportManager(private val plugin: RandomTeleportPlugin) {

    private fun getRandomInt() = Randoms.getRandomInt(plugin.config.randomTeleportMin, plugin.config.randomTeleportMax)

    private fun getWorld() = Bukkit.getWorlds()[0]

    fun findSafeTeleportLocation(): Location {
        val block = findSaveBlock()
        val location = block.location
        if (SpawnAPI.isCuboidProtection(location)) return findSafeTeleportLocation()
        if (GuildAPI.getGuildByCords(location.blockX, location.blockZ) != null) return findSafeTeleportLocation()
        if (!Locations.isInLocation(location.world.worldBorder.center,location.world.worldBorder.size.toInt(),location)) return findSafeTeleportLocation()
        return location
    }

    private fun findSaveBlock(): Block {
        val randomX = getRandomInt()
        val randomZ = getRandomInt()
        val world = getWorld()
        val block = world.getHighestBlockAt(randomX, randomZ)
        val blockBelow = block.location.subtract(0.0,1.0,0.0).block
        return if (! plugin.config.safeBlocks.contains(blockBelow.type.id)) findSaveBlock() else block

    }


}