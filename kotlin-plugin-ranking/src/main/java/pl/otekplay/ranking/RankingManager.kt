package pl.otekplay.ranking

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.SkullType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.plugin.builders.ItemBuilder
import pl.otekplay.plugin.rep
import java.util.*
import kotlin.collections.ArrayList


class RankingManager(private val plugin: RankingPlugin) {
    private val comparator = RankingComparator()
    val rankings = ArrayList<Ranking>()
    internal val rankingsByKills get() = rankings.sortedByDescending { it.kills }
    internal val rankingsByDeaths get() = rankings.sortedByDescending { it.deaths }
    internal val rankingsByAssists get() = rankings.sortedByDescending { it.assists }

    init {
        DatabaseAPI.loadAll<Ranking>("rankings", {
            rankings.addAll(it)
            plugin.logger.info("Loaded ${rankings.size} rankings")
            sortRankings()
        })
    }

    fun createRanking(uniqueId: UUID) {
        val ranking = Ranking(uniqueId, plugin.config.rankingStartPoints, 0, 0, 0)
        ranking.insertEntity()
        rankings.add(ranking)
    }

    fun getPlace(uniqueId: UUID) = rankings.indexOf(getRanking(uniqueId)) + 1

    fun getRanking(place: Int) = if (place >= rankings.size) null else rankings[place]

    fun getRanking(uniqueId: UUID) = rankings.singleOrNull { it.uniqueId == uniqueId }

    fun sortRankings() = rankings.sortWith(comparator)

    fun openTopMenu(player: Player, config: ConfigTopMenu, list: List<Ranking>) {
        val menu = Menu(config.options)
        val size = if (config.sizeTop > list.size) list.size - 1 else config.sizeTop
        0.rangeTo(size).forEach {
            val rank = list[it]
            val place = list.indexOf(rank)
            menu.setItem(it, Item(buildTopItemFromConfig(rank, config,place+1)))
        }
        menu.open(player)
    }

    private fun buildTopItemFromConfig(ranking: Ranking, config: ConfigTopMenu, place: Int): ItemStack {
        val item = ItemBuilder(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal.toShort()).buildItemStack()
        val meta = item.itemMeta as SkullMeta
        val player = Bukkit.getOfflinePlayer(ranking.uniqueId)
        meta.owner = player.name
        meta.displayName = config.itemName
                .rep("%kills%", ranking.kills.toString())
                .rep("%place%", place.toString())
                .rep("%points%", ranking.points.toString())
                .rep("%deaths%", ranking.deaths.toString())
                .rep("%assists%", ranking.assists.toString())
                .rep("%name%", player.name)
        val lore = ArrayList(config.itemLore)
        lore.replaceAll { it
                    .rep("%kills%", ranking.kills.toString())
                    .rep("%place%", place.toString())
                    .rep("%points%", ranking.points.toString())
                    .rep("%deaths%", ranking.deaths.toString())
                    .rep("%assists%", ranking.assists.toString())
                    .rep("%name%", player.name)
        }
        meta.lore = lore
        item.itemMeta = meta
        return item
    }


}