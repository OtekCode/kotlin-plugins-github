package pl.otekplay.generator

import com.google.gson.annotations.SerializedName
import org.bukkit.Location
import org.bukkit.Material
import pl.otekplay.database.DatabaseEntity
import pl.otekplay.guild.GuildAPI
import java.util.*

data class Generator(
        @SerializedName("id")
        val uniqueId: UUID,
        val location: Location,
        val buildMaterial: Material,
        val buildTime: Long,
        var repairTime: Long
) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "generators"

    fun needRepair() = System.currentTimeMillis() >= repairTime

    fun canRepair(): Boolean {
        val guild = GuildAPI.getGuildByLocation(location)?: return true
        return !GuildAPI.isGuildBlockedByExplode(guild)
    }

    fun repairGenerator() { location.world.getBlockAt(location.blockX, location.blockY + 1, location.blockZ).type = buildMaterial }

}


