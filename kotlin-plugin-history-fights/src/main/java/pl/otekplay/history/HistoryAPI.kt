package pl.otekplay.history

object HistoryAPI {
    internal lateinit var plugin:HistoryPlugin


    fun addHistory(fight: HistoryFight) = plugin.manager.addHistory(fight)
}