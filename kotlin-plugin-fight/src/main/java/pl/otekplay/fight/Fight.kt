package pl.otekplay.fight

import java.util.*
import kotlin.collections.ArrayList

class Fight(val uniqueId: UUID) {
    private val _attacks = ArrayList<FightAttack>()
    val lastAttack get() =  _attacks.maxBy {it.time  }?.time ?: -1L
    val attacks: List<FightAttack> get() = Collections.unmodifiableList(_attacks)

    fun attack(uniqueId: UUID, damage: Double, type: FightAttackType) = _attacks.add(FightAttack(uniqueId, type, damage, System.currentTimeMillis()))

    fun clear() = _attacks.clear()


}