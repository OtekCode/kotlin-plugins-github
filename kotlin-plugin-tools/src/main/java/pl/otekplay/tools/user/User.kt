package pl.otekplay.tools.user

import pl.otekplay.database.DatabaseEntity
import java.util.*

data class User(
        val uniqueId: UUID,
        val nickname: String,
        val firstJoinTime: Long,
        var lastJoinTime: Long,
        var lastQuitTime: Long
): DatabaseEntity(){
    override val id: String
        get() = uniqueId.toString()
    override val key: String
        get() = "uniqueId"
    override val collection: String
        get() = "players"

}