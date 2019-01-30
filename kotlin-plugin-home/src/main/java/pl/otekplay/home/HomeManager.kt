package pl.otekplay.home

import org.bukkit.entity.Player
import pl.otekplay.database.DatabaseAPI
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class HomeManager(private val plugin: HomePlugin) {
    private val homes = ConcurrentHashMap<UUID, HomePlayer>()

    init {
        DatabaseAPI.loadAll<HomePlayer>("home_players") {
            it.forEach { homes[it.uniqueId] = it }
            plugin.logger.info("Loaded ${homes.size} homes")
        }

    }

    fun createHome(uniqueId: UUID) = run { homes[uniqueId] = HomePlayer(uniqueId).apply { insertEntity() } }

    fun getHome(uniqueId: UUID) = homes[uniqueId]

    fun getPlayerHomesLimit(player: Player) = plugin.config.homesLimit.filter { player.hasPermission(it.permission) }.maxBy { it.priority }?.limitHomes
            ?: plugin.config.defaultLimitHome
}