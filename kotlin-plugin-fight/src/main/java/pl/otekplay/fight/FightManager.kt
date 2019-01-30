package pl.otekplay.fight

import java.util.*

class FightManager(val plugin: FightPlugin) {
    private val _fights = HashMap<UUID, Fight>()
    private val fights get() = _fights.values
    val validFights get() = fights.filter { isFighting(it) }

    fun registerFight(uniqueId: UUID) = Fight(uniqueId).also { _fights[uniqueId] = it }

    fun unregisterFight(uniqueId: UUID) = _fights.remove(uniqueId)

    fun getFight(uniqueId: UUID) = _fights[uniqueId]

    fun isFighting(fight: Fight) = (fight.lastAttack + plugin.config.validAttackerTime) > System.currentTimeMillis()

}