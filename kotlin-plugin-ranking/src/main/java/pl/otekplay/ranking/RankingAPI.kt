package pl.otekplay.ranking

import java.util.*

object RankingAPI {
    internal lateinit var plugin: RankingPlugin

    fun getRanking(uniqueId: UUID) = plugin.manager.getRanking(uniqueId)

    fun sortRankings() = plugin.manager.sortRankings()

    fun getPlace(uniqueId: UUID) = plugin.manager.getPlace(uniqueId)

    fun getRanking(place: Int) = plugin.manager.getRanking(place)
}