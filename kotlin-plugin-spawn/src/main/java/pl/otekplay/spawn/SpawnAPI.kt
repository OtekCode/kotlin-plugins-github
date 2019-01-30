package pl.otekplay.spawn

import org.bukkit.Location

object SpawnAPI {
    internal lateinit var plugin: SpawnPlugin

    fun isCuboidProtection(location: Location) = plugin.isCuboidProtection(location)

    fun isPvPProtection(location: Location) = plugin.isPvPProtection(location)

    val spawnLocation get() = plugin.spawnLocation

    val cuboidRadiusPvP get() = plugin.config.cuboidProtectionRadius

    val cuboidRadiusCuboid get() = plugin.config.pvpProtectionRadius
}