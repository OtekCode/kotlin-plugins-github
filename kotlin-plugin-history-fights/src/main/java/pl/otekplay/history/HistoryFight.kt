package pl.otekplay.history

import org.bukkit.Bukkit
import pl.otekplay.database.DatabaseEntity
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import java.util.*

data class HistoryFight(
        val historyId:UUID,
        val deathId:UUID,
        val points:Int,
        val receivedDamage:Int,
        val startTime:Long,
        val endTime:Long,
        val killer:HistoryAttacker,
        val assists:Map<UUID,HistoryAttacker>
): DatabaseEntity() {
    override val id: String
        get() = historyId.toString()
    override val key: String
        get() = "historyId"
    override val collection: String
        get() = "histories"


    fun replaceString(string: String) = string
            .rep("%id%",historyId.toString())
            .rep("%death%", Bukkit.getOfflinePlayer(deathId).name)
            .rep("%minus%",points.toString())
            .rep("%received%",receivedDamage.toString())
            .rep("%start%", Dates.formatData(startTime))
            .rep("%end%",Dates.formatData(endTime))
            .rep("%killername%",Bukkit.getOfflinePlayer(killer.uniqueId).name)
            .rep("%assists%",assists.size.toString())
}