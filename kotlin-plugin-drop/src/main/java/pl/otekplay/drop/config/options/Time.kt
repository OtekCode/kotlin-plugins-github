package pl.otekplay.drop.config.options

import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.rep

data class Time(
        val startTime: String,
        val enabled: Boolean,
        val from: String,
        val to: String
) {
    private val startTimeLong get() = Dates.parseData(startTime).time

    private val fromInt get() = from.rep(":", "").toInt()

    private val toInt get() = to.rep(":", "").toInt()

    fun hasStarted() = System.currentTimeMillis() > startTimeLong

    fun isAvailable() = if (enabled) Dates.isBeetwenHours(fromInt, toInt, Dates.dateInt()) else true
}


