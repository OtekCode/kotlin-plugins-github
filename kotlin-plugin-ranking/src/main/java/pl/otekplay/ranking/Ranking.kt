package pl.otekplay.ranking

import pl.otekplay.database.DatabaseEntity
import java.util.*

class Ranking(
        val uniqueId: UUID,
        var points: Int,
        var kills: Int,
        var deaths: Int,
        var assists: Int
) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "rankings"
}