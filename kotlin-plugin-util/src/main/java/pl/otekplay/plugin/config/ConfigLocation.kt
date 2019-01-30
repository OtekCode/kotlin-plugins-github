package pl.otekplay.plugin.config

import org.bukkit.Bukkit
import org.bukkit.Location

class ConfigLocation(val x: Double, val y: Double, val z: Double, private val yaw: Float, private val pitch: Float) {
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble(), 0F, 0F)
    constructor(x: Int, y: Int, z: Int, yaw: Float, pitch: Float) : this(x.toDouble(), y.toDouble(), z.toDouble(), yaw, pitch)
    constructor(location: Location) : this(location.x,location.y,location.z,location.yaw,location.pitch)

    fun toLocation() = Location(Bukkit.getWorlds()[0],x,y,z,yaw,pitch)
}