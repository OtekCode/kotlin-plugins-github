package pl.otekplay.bans

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.database.DatabaseAPI

class BanManager(val plugin: BanPlugin) {
    private val _bans = arrayListOf<Ban>()
    private val bans get() = _bans.filter { ! it.expired }
    private val ipBans get() = bans.filter { it.type == BanType.IP }
    private val idBans get() = bans.filter { it.type == BanType.PLAYER }

    init {
        DatabaseAPI.loadAll<Ban>("bans") {
            it.filter { it.expired }.forEach { it.deleteEntity() }
            val bans = it.filter { ! it.expired }
            _bans.addAll(bans)
            plugin.logger.info("Loaded ${_bans.size} bans!")

        }
    }

    fun getBan(entry: String, type: BanType) = if (type == BanType.IP) ipBans.singleOrNull { it.entry == entry} else idBans.singleOrNull{ it.entry == entry }


    private fun createBan(entry: String, source: String, type: BanType, time: Long, reason: String) = Ban(entry, type, System.currentTimeMillis(), time, reason, source).also {
        it.insertEntity()
        _bans.add(it)
    }

    fun removeBan(ban: Ban) {
        ban.deleteEntity()
        _bans.remove(ban)
    }

    fun banIp(p: Player, source: String, time: Long, reason: String) = createBan(
            p.address.address.hostAddress,
            source,
            BanType.IP,
            time,
            reason
    )

    fun banId(op: OfflinePlayer, source: String, time: Long, reason: String) = createBan(
            op.uniqueId.toString(),
            source,
            BanType.PLAYER,
            time,
            reason
    )

    fun kickPlayerAfterBan(player: Player, ban: Ban) {
        val message = arrayListOf(*plugin.config.formatLoginDisallowBanned.toTypedArray())
        message.replaceAll { ban.replaceString(it) }
        player.kickPlayer(message.joinToString { "\n" + it })
    }
}