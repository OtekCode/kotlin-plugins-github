package pl.otekplay.farmer

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable

class FarmerTask(private val plugin: FarmerPlugin, private val farmer: ConfigFarmer, location: Location, private val done: Int) : BukkitRunnable() {
    private var lastLocation = location
    override fun run() {
        buildNext()
    }

    private fun buildNext() {
        val buildLocation = if (farmer.buildDown) lastLocation.clone().subtract(0.0, 1.0, 0.0) else lastLocation.clone().add(0.0, 1.0, 0.0)
        if (buildLocation.y > 250) return
        if (0 > buildLocation.y) return
        if (farmer.stopOnBlock && buildLocation.block.type != Material.AIR) return
        if (farmer.stopOnBedrock && buildLocation.block.type == Material.BEDROCK) return
        val material = Material.getMaterial(farmer.buildId)
        buildLocation.block.type = material
        val nextDone = done + 1
        if (farmer.buildLimit == - 1) return
        if (nextDone >= farmer.buildLimit) return
        plugin.taskLaterSync(FarmerTask(plugin, farmer, buildLocation, nextDone), farmer.buildTime)
    }

}