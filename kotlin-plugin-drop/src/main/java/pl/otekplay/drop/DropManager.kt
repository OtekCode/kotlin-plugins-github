package pl.otekplay.drop

import org.bukkit.block.Biome
import org.bukkit.block.Block
import org.bukkit.entity.Player
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.drop.config.DropHolder
import pl.otekplay.drop.config.DropItem
import pl.otekplay.drop.config.bonus.Bonus
import pl.otekplay.drop.config.bonus.Fortune
import pl.otekplay.drop.config.bonus.Perm
import pl.otekplay.drop.config.options.*
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class DropManager(val plugin: DropPlugin) {
    val drops = CopyOnWriteArrayList<Drop>()
    private val users = HashMap<UUID, DropUser>()

    init {
        saveDrops()
        loadDrops()
        DatabaseAPI.loadAll<DropUser>("drop_users") {
            it.forEach { users[it.uniqueId] = it }
            plugin.logger.info("Loaded ${users.size} drop users.")
        }

    }

    fun createUser(uniqueId: UUID) {
        users[uniqueId] = DropUser(uniqueId).also { it.insertEntity() }
    }

    fun getUser(uniqueId: UUID) = users[uniqueId]

    fun getNeedToNextLevel(user: DropUser): Int {
        val nextLevel = plugin.config.levelUpgrades[user.level + 1] ?: return -1
        return if (user.exp > nextLevel) 0 else nextLevel - user.exp
    }

    fun shouldUpgradeDropLevel(user: DropUser) = getNeedToNextLevel(user) == 0


    fun getAvailableDrops(player: Player, user: DropUser, block: Block): List<Drop> {
        val hand = player.itemInHand
        val id = block.typeId
        val location = block.location
        return drops
                .filter { it.time.hasStarted() && it.time.isAvailable() }
                .filter { it.info.breakIds.contains(id) }
                .filter { if (it.info.permission == "") true else player.hasPermission(it.info.permission) }
                .filter { !it.isBlockedByUser(user) }
                .filter { it.info.height.isValidLocation(location) }
                .filter { it.info.pickAxe.checkValidPickAxe(hand) }
                .filter { if (it.info.biomes.isEmpty()) true else it.info.biomes.contains(block.biome) }

    }

    fun getDrops(player: Player, user: DropUser, drops: List<Drop>) = drops.mapNotNull { it.isDropped(player, user) }


    private fun saveDrops() {
        val exp = DropHolder(
                Info("exampleExp", 100.0, 2, 3, "", arrayOf(1), Height(true, MinMax(10, 60)), PickAxe(true, MinMax(0, 5)), MinMax(1, 5), listOf(Biome.BEACH)),
                DropItem(false, 0, 0),
                Time(Dates.formatData(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3)), true, "10:00", "22:00"),
                listOf(),
                Bonus(true, 0.5, 0, 1, 0), listOf())
        Files.saveJson(File(plugin.folder, exp.info.name), plugin.pluginLoader.gson.toJson(exp))
        val drop = DropHolder(
                Info("exampleFull", 0.5, 2, 3, "", arrayOf(1), Height(true, MinMax(10, 60)), PickAxe(true, MinMax(0, 5)), MinMax(0, 5), listOf()),
                DropItem(true, 264, 0),
                Time(Dates.formatData(System.currentTimeMillis()), true, "18:00", "01:00"),
                listOf(
                        Fortune(true, 1, 0.5, 5, 1, 0),
                        Fortune(true, 2, 1.0, 10, 1, 0),
                        Fortune(true, 3, 1.5, 15, 1, 0)
                ),
                Bonus(true, 0.5, 5, 1, 0),
                listOf(
                        Perm(true, "drop.vip", 0, 0.5, 5, 1, 0),
                        Perm(true, "drop.svip", 1, 1.0, 10, 1, 0)
                )
        )
        Files.saveJson(File(plugin.folder, drop.info.name), plugin.pluginLoader.gson.toJson(drop))

    }

    fun loadDrops() {
        drops.clear()
        plugin.folder
                .listFiles()
                .filter { !it.name.contains("Config") }
                .forEach {
                    plugin.logger.info("Loading ${it.name} drop file...")
                    val json = plugin.pluginLoader.gson.fromJson(it.readText(), DropHolder::class.java)
                    if (!json.info.name.contains("example")) drops.add(json.toLoaded())
                }
        plugin.logger.info("Loaded " + drops.size + " drops.")
    }
}