package pl.otekplay.drop

import org.bukkit.Material
import pl.otekplay.database.DatabaseEntity
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

data class DropUser(
        val uniqueId: UUID
) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "drop_users"
    private val disabledDrops = HashSet<Material>()
    private val droppedDrops = HashMap<Int, Long>()
    var level: Int = 1
    var exp: Int = 0
    var turboTime = System.currentTimeMillis()
    val hasTurbo get() = turboTime > System.currentTimeMillis()

    fun isDisabled(material: Material) = disabledDrops.contains(material)

    fun disableDrop(material: Material) = disabledDrops.add(material).run { updateEntity() }

    fun enableDrop(material: Material) = disabledDrops.remove(material).run { updateEntity() }

    fun collectDrops(material: Material, amount: Long) = addDrops(material.id, amount)

    private fun addDrops(id: Int, amount: Long) {
        if (!droppedDrops.containsKey(id))
            droppedDrops[id] = amount
        else
            droppedDrops[id] = getDrops(id) + amount
    }

    fun getDrops(id: Int) = droppedDrops[id] ?: 0L

    fun getDrops(material: Material) = getDrops(material.id)

    fun upgradeLevel() {
        level++
        exp = 0
    }

}