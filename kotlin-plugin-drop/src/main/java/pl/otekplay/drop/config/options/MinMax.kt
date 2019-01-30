package pl.otekplay.drop.config.options

import pl.otekplay.plugin.util.Randoms

data class MinMax(
        val min: Int,
        val max: Int
) {
    fun isBetween(int: Int) = int in min..max

    fun getValue() = if (min >= max) min else Randoms.getRandomInt(min, if (max > 64) 64 else max)

}
