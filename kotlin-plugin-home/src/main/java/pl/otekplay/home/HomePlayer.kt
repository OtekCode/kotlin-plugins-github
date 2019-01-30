package pl.otekplay.home

import org.bukkit.Location
import pl.otekplay.database.DatabaseEntity
import java.util.*
import kotlin.collections.HashMap

data class HomePlayer(
        val uniqueId: UUID
) : DatabaseEntity() {
    override val collection: String
        get() = "home_players"
    override val key: String
        get() = "uniqueId"
    override val id: String
        get() = uniqueId.toString()
    private val _homes = HashMap<String, Home>()
    var lastSetHomeTime = System.currentTimeMillis()
    var lastHomeTeleportTime = System.currentTimeMillis()
    val homes get() = _homes.values

    fun getHome(name: String) = _homes[name]

    fun setHome(homeName: String, location: Location) {
        _homes[homeName] = Home(homeName, location, System.currentTimeMillis())
        updateEntity()
    }

    fun hasLimitHomes(limit:Int) = _homes.size >= limit
}