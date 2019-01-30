package pl.otekplay.drop.config.bonus

import org.bukkit.entity.Player

class Perm(
        enabled: Boolean,
        val permission: String,
        val priority: Int,
        chance: Double,
        dropExp: Int,
        playerExp: Int,
        amount: Int
) : Bonus(
        enabled,
        chance,
        dropExp,
        playerExp,
        amount
) {
    fun hasPermission(player: Player): Boolean {
        if (!enabled) return true
        return player.hasPermission(permission)
    }
}
