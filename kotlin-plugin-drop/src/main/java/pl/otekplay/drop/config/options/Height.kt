package pl.otekplay.drop.config.options

import org.bukkit.Location

data class Height(
        val check: Boolean,
        val minMax: MinMax
) {

    fun isValidLocation(location: Location) = if (! check) true else minMax.isBetween(location.blockY)

}