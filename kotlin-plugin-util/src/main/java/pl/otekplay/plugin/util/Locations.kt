package pl.otekplay.plugin.util

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*


object Locations {
    fun getSquare(center: Location, radius: Int): MutableList<Location> {
        val locs = ArrayList<Location>()
        val cX = center.blockX
        val cZ = center.blockZ
        val minX = Math.min(cX + radius, cX - radius)
        val maxX = Math.max(cX + radius, cX - radius)
        val minZ = Math.min(cZ + radius, cZ - radius)
        val maxZ = Math.max(cZ + radius, cZ - radius)

        for (x in minX..maxX)
            for (z in minZ..maxZ)
                locs.add(Location(center.world, x.toDouble(), center.y, z.toDouble()))
        locs.add(center)
        return locs
    }

    private fun getCorners(center: Location, radius: Int): MutableList<Location> {
        val locations = ArrayList<Location>()
        val cX = center.blockX
        val cZ = center.blockZ
        val minX = Math.min(cX + radius, cX - radius)
        val maxX = Math.max(cX + radius, cX - radius)
        val minZ = Math.min(cZ + radius, cZ - radius)
        val maxZ = Math.max(cZ + radius, cZ - radius)

        locations.add(Location(center.world, minX.toDouble(), center.y, minZ.toDouble()))
        locations.add(Location(center.world, maxX.toDouble(), center.y, minZ.toDouble()))
        locations.add(Location(center.world, minX.toDouble(), center.y, maxZ.toDouble()))
        locations.add(Location(center.world, maxX.toDouble(), center.y, maxZ.toDouble()))
        return locations
    }

    fun getWalls(center: Location, radius: Int): MutableList<Location> {
        val locs = getSquare(center, radius)
        locs.removeAll(getSquare(center, radius - 1))
        return locs
    }

    fun getSquare(center: Location, radius: Int, height: Int): List<Location> {
        val locs = getSquare(center, radius)
        for (i in 1..height)
            locs.addAll(getSquare(Location(center.world, center.x, center.y + i, center.z), radius))
        return locs
    }

    fun getCorners(center: Location, radius: Int, height: Int): List<Location> {
        val locs = getCorners(center, radius)
        for (i in 1..height)
            locs.addAll(getCorners(Location(center.world, center.x, center.y + i, center.z), radius))
        return locs
    }

    fun getWallsOnGround(center: Location, radius: Int) = getWalls(center, radius).map { it.world.getHighestBlockAt(it).location.add(0.0, 1.0, 0.0) }


    fun getWalls(center: Location, radius: Int, height: Int): List<Location> {
        val locs = getWalls(center, radius)
        for (i in 1..height)
            locs.addAll(getWalls(Location(center.world, center.x, center.y + i, center.z), radius))
        return locs
    }

    fun getSphere(loc: Location, radius: Int, height: Int, hollow: Boolean, sphere: Boolean, plusY: Int): List<Location> {
        val circleblocks = ArrayList<Location>()
        val cx = loc.blockX
        val cy = loc.blockY
        val cz = loc.blockZ

        for (x in cx - radius..cx + radius) {
            for (z in cz - radius..cz + radius) {
                for (y in (if (sphere) cy - radius else cy) until if (sphere) cy + radius else cy + height) {
                    val dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + if (sphere) (cy - y) * (cy - y) else 0).toDouble()

                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        val l = Location(loc.world, x.toDouble(), (y + plusY).toDouble(), z.toDouble())
                        circleblocks.add(l)
                    }
                }
            }
        }
        return circleblocks
    }

    fun isInLocation(center: Location, radius: Int, location: Location) = isInLocation(center.blockX, center.blockZ, radius, location.blockX, location.blockZ)

    private fun isInLocation(centerX: Int, centerZ: Int, radius: Int, locX: Int, locZ: Int) = Math.abs(centerX - locX) <= radius && Math.abs(centerZ - locZ) <= radius


    fun pushAwayFrom(location: Location, player: Player, speed: Double) {
        player.velocity = player.location.toVector().subtract(location.toVector()).normalize().multiply(speed)
    }


}