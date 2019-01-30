package pl.otekplay.permissions

import pl.otekplay.database.DatabaseEntity
import java.util.*
import kotlin.collections.ArrayList

data class PermissionsUser(
        val uniqueId: UUID,
        var group: String
) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "permissions_users"
    var permissions = ArrayList<String>()
    var superuser: Boolean = false
}