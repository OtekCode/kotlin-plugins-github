package pl.otekplay.fight

import java.util.*

object FightAPI {
    internal lateinit var plugin: FightPlugin

    fun getFight(uniqueId: UUID) = plugin.manager.getFight(uniqueId)
}