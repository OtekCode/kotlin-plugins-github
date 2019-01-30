package pl.otekplay.backup

import pl.otekplay.database.DatabaseEntity
import java.util.*

data class PlayerBackup(
        val uniqueId: UUID,
        val backups: MutableList<Backup>
) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "backups"
}