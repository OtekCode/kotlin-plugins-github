package pl.otekplay.kit

import pl.otekplay.database.DatabaseEntity
import java.util.*

data class KitUser(
        val uniqueId: UUID,
        val takenKits: HashMap<String, Long>
) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "kitusers"

    fun canTake(kit: Kit): Boolean {
        val long = takenKits[kit.name] ?: return true
        if(long > System.currentTimeMillis()) return false
        takenKits.remove(kit.name)
        updateEntity()
        return true
    }

    fun getCooldownTime(kit: Kit) = takenKits[kit.name] ?: -1L

    fun takeKit(kit: Kit){
        takenKits[kit.name] = System.currentTimeMillis() + kit.delayTime
        updateEntity()
    }
}