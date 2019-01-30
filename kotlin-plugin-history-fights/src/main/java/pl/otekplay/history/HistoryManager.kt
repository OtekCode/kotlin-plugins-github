package pl.otekplay.history

import pl.otekplay.database.DatabaseAPI
import java.util.*

class HistoryManager(val plugin: HistoryPlugin) {
    private val histories = arrayListOf<HistoryFight>()

    init {
        DatabaseAPI.loadAll<HistoryFight>("histories", {
            histories.addAll(it)
            plugin.logger.info("Loaded ${histories.size} histories.")
        })
    }

    fun addHistory(fight: HistoryFight) = histories.add(fight).also { fight.insertEntity() }

    fun getDeathHistories(uniqueId: UUID) = histories.filter { it.deathId == uniqueId }

    fun getKillerHistories(uniqueId: UUID) = histories.filter { it.killer.uniqueId == uniqueId }

    fun getAssistsHistories(uniqueId: UUID) = histories.filter { it.assists.containsKey(uniqueId) }

//    fun getHistories(uniqueId: UUID) = arrayListOf(
//            *getDeathHistories(uniqueId).toTypedArray(),
//            *getKillerHistories(uniqueId).toTypedArray(),
//            *getDeathHistories(uniqueId).toTypedArray()
//    )

}