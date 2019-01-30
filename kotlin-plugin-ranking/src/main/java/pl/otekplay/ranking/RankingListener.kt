package pl.otekplay.ranking

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import pl.otekplay.chat.ChatFormatEvent
import pl.otekplay.fight.Fight
import pl.otekplay.fight.FightAPI
import pl.otekplay.fight.FightAttack
import pl.otekplay.history.HistoryAPI
import pl.otekplay.history.HistoryAttacker
import pl.otekplay.history.HistoryFight
import pl.otekplay.plugin.rep
import java.util.*
import kotlin.collections.HashMap


class RankingListener(private val plugin: RankingPlugin) : Listener {

    @EventHandler
    fun onChatFormat(e: ChatFormatEvent) {
        val ranking = plugin.manager.getRanking(e.sender.uniqueId) ?: return
        e.format = e.format.rep("%rank%", "${ranking.points}")
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (plugin.manager.getRanking(e.player.uniqueId) == null) plugin.manager.createRanking(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.NORMAL)
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val player = e.entity
        val fight = FightAPI.getFight(player.uniqueId) ?: return
        calculateRanking(player.uniqueId, fight)
    }


    private fun calculateRanking(deathId: UUID, fight: Fight) {
        val parts = HashMap<UUID, Double>()
        val attacks = fight.attacks
        if (attacks.isEmpty()) return
        val totalDamage = attacks.sumByDouble { it.damage }.toInt()
        attacks.forEach {
            val id = it.uniqueId
            parts[id] = fight.attacks.filter { it.uniqueId == id }.sumByDouble { it.damage }
        }
        val percentDamage = HashMap<UUID, Double>()
        parts.forEach {
            val per = 100 * it.value / totalDamage
            if (per > plugin.config.minPercentToValidAssist)
                percentDamage[it.key] = per
        }

        val deathRanking = plugin.manager.getRanking(deathId) ?: return
        val deathPoints = deathRanking.points
        val partsRanking = parts.mapNotNull { plugin.manager.getRanking(it.key) }
        if (partsRanking.isEmpty()) return
        val totalPoints = partsRanking.sumBy { it.points } / partsRanking.size
        val plus: Int
        val minus: Int
        val percent = deathRanking.points * 0.05
        val value: Double
        if (totalPoints <= deathPoints) {
            value = (deathPoints - totalPoints) / totalPoints + 1.0
            plus = Math.round(percent * value).toInt()
            minus = Math.round(percent).toInt()
        } else {
            value = (totalPoints - deathPoints) / deathPoints + 1.0
            plus = Math.round(percent / value).toInt()
            minus = Math.round(percent / (value * value)).toInt()
        }
        deathRanking.deaths++
        deathRanking.points = deathRanking.points - minus
        deathRanking.updateEntity()
        val killerRanking = partsRanking.singleOrNull { parts.maxBy { it.value }?.key == it.uniqueId } ?: return
        val killerPercent = parts[killerRanking.uniqueId] ?: return
        val killerPoints = (plus * killerPercent / 100).toInt()
        killerRanking.kills++
        killerRanking.points += killerPoints
        killerRanking.updateEntity()
        Bukkit.broadcastMessage(plugin.config.messages.playerKilledBy
                .rep("%death%", Bukkit.getOfflinePlayer(deathRanking.uniqueId).name)
                .rep("%killer%", Bukkit.getOfflinePlayer(killerRanking.uniqueId).name)
                .rep("%plus%", plus.toString())
                .rep("%minus%", minus.toString()))
        val othersRanking = partsRanking.filter { it.uniqueId != killerRanking.uniqueId }
        val killerHistory = buildHistoryAttackerFromAttacks(killerRanking.uniqueId, killerPoints, totalDamage, attacks)
        val assistsHistory = arrayListOf<HistoryAttacker>()
        if (othersRanking.isNotEmpty()) {
            othersRanking.forEach {
                val assistPercent = parts[it.uniqueId] ?: return
                val assistPoints = ((plus * assistPercent) / 100).toInt()
                it.assists++
                it.points += assistPoints
                it.updateEntity()
                assistsHistory.add(buildHistoryAttackerFromAttacks(it.uniqueId, assistPoints, totalDamage, attacks))
                Bukkit.broadcastMessage(plugin.config.messages.playerKillerAssisted
                        .rep("%death%", Bukkit.getOfflinePlayer(deathRanking.uniqueId).name)
                        .rep("%assist%", Bukkit.getOfflinePlayer(it.uniqueId).name)
                        .rep("%plus%", assistPoints.toString())
                        .rep("%minus%", deathPoints.toString()))
            }
        }
        val gather = assistsHistory.plus(killerHistory)
        val history = buildHistoryFight(deathId,
                minus,
                totalDamage,
                gather.minBy { it.firstAttackTime }?.firstAttackTime ?: System.currentTimeMillis(),
                gather.minBy { it.lastAttackTime }?.lastAttackTime ?: System.currentTimeMillis(),
                killerHistory,
                assistsHistory.map { Pair(it.uniqueId, it) }.toMap())
        plugin.manager.sortRankings()
        HistoryAPI.addHistory(history)
    }

    private fun buildHistoryFight(uniqueId: UUID, points: Int, totalReceived: Int, firstAttack: Long, lastAttack: Long, killer: HistoryAttacker, assists: Map<UUID, HistoryAttacker>) =
            HistoryFight(
                    UUID.randomUUID(),
                    uniqueId,
                    points,
                    totalReceived,
                    firstAttack,
                    lastAttack,
                    killer,
                    assists)


    private fun buildHistoryAttackerFromAttacks(uniqueId: UUID, points: Int, totalReceived: Int, attacks: List<FightAttack>): HistoryAttacker {
        val playerAttacks = attacks.filter { it.uniqueId == uniqueId }
        val firstAttack = playerAttacks.minBy { it.time }?.time ?: System.currentTimeMillis()
        val lastAttack = playerAttacks.maxBy { it.time }?.time ?: System.currentTimeMillis()
        val maximumDamage = playerAttacks.maxBy { it.damage }?.damage ?: 0.0
        val minimumDamage = playerAttacks.minBy { it.damage }?.damage ?: 0.0
        val totalDamage = playerAttacks.sumBy { it.damage.toInt() }
        val averageDamage = totalDamage / playerAttacks.size
        val percentDamage = 100 * totalDamage / totalReceived
        return HistoryAttacker(uniqueId, points
                , maximumDamage.toInt()
                , minimumDamage.toInt()
                , averageDamage
                , firstAttack
                , lastAttack
                , totalDamage
                , percentDamage
                , attacks.map { Pair(it.time, it.damage.toInt()) }.toMap())
    }
}