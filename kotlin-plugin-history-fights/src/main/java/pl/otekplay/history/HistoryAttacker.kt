package pl.otekplay.history

import org.bukkit.Bukkit
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import java.util.*

data class HistoryAttacker(
        val uniqueId: UUID,
        val points: Int,
        val maximumDamage:Int,
        val minimumDamage:Int,
        val averageDamage:Int,
        val firstAttackTime:Long,
        val lastAttackTime:Long,
        val totalDamage:Int,
        val percentDamage:Int,
        val attacks: Map<Long,Int>
){

    fun replaceString(string: String) = string
            .rep("%name%", Bukkit.getOfflinePlayer(uniqueId).name)
            .rep("%plus%",points.toString())
            .rep("%maximum%",maximumDamage.toString())
            .rep("%minimum%",minimumDamage.toString())
            .rep("%average%",averageDamage.toString())
            .rep("%firstAttack%", Dates.formatData(firstAttackTime))
            .rep("%lastAttack%",Dates.formatData(lastAttackTime))
            .rep("%total%",totalDamage.toString())
            .rep("%percent%",percentDamage.toString())
            .rep("%attacks%",attacks.size.toString())
}