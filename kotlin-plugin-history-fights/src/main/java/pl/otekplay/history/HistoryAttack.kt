package pl.otekplay.history

import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates

data class HistoryAttack(
        val damage:Double,
        val time:Long
){

    fun replaceString(string: String) = string.rep("%time%", Dates.formatData(time)).rep("%damage%",damage.toInt().toString())
}