package pl.otekplay.fight

import java.util.*

class FightAttack(
        val uniqueId: UUID,
        val type: FightAttackType,
        val damage: Double,
        val time: Long
)