package pl.otekplay.guild.holders

import org.bukkit.Location
import pl.otekplay.database.DatabaseEntity
import java.util.*

data class Guild(
        val guildId: UUID,
        val tag: String,
        val name: String,
        val center: Location,
        var home: Location = center,
        var leaderId: UUID,
        val createDate: Long,
        var lives: Int,
        var lastDestroyed: Long,
        var cuboidSize: Int,
        var membersSize: Int,
        var expireTime: Long,
        val members: HashSet<GuildMember>,
        var allyGuilds: HashSet<UUID>,
        val allyInvites: HashSet<UUID>,
        internal val invites: HashSet<UUID>
) : DatabaseEntity() {
    override val id: String get() = guildId.toString()
    override val key: String get() = "guildId"
    override val collection: String get() = "guilds"

    val options = GuildOptions(true, true)

    fun getMember(uniqueId: UUID) = members.singleOrNull { it.uniqueId == uniqueId }

    fun isLeader(uniqueId: UUID) = leaderId == uniqueId


}
