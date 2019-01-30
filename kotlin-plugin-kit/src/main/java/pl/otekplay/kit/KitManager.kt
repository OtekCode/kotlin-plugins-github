package pl.otekplay.kit

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

class KitManager(private val plugin: KitPlugin) {
    private val users = hashMapOf<UUID, KitUser>()
    val kits = arrayListOf<Kit>()

    init {
        saveExampleKit()
        loadKits()
        loadUsers()
    }

    fun createUser(uniqueId: UUID) = run {
        users[uniqueId] = KitUser(uniqueId, hashMapOf()).also { it.insertEntity() }
    }

    private fun getKit(name: String) = kits.singleOrNull { it.name.equals(name, true) }

    fun getUser(uniqueId: UUID) = users[uniqueId]

    private fun saveExampleKit() {
        val example = Kit(
                "example",
                "example.example",
                Dates.formatData(System.currentTimeMillis()),
                TimeUnit.DAYS.toMillis(1),
                ConfigMenuItem(5, ConfigItem("ExampleKit", Material.LEATHER_HELMET.id, 1, 0, arrayListOf(
                        "Prawo: %perm%",
                        "Dostepny: %startdate%",
                        "Blokada: %cooldown%",
                        "Mozesz: %available%"
                ))),
                arrayListOf(ConfigEnchantedItem("SuperHelm", Material.LEATHER_HELMET.id, 1, 0, arrayListOf(), hashMapOf(Pair(Enchantment.DURABILITY.id, 1))))
        )
        Files.saveJson(File(plugin.folder, example.name), plugin.pluginLoader.gson.toJson(example))

    }

    fun loadKits() {
        kits.clear()
        plugin.folder
                .listFiles()
                .filter { !it.name.contains("Config") }
                .forEach {
                    plugin.logger.info("Loading ${it.name} kit...")
                    val json = plugin.pluginLoader.gson.fromJson(it.readText(), Kit::class.java)
                    if (!json.name.contains("example")) kits.add(json)
                }
        plugin.logger.info("Loaded " + kits.size + " kits.")
    }

    private fun loadUsers() {
        DatabaseAPI.loadAll<KitUser>("kitusers") {
            it.forEach { users[it.uniqueId] = it }
            plugin.logger.info("Loaded ${users.size} kit users!")
            cleanInvalidKits()
        }
    }

    fun cleanInvalidKits() {
        users.values.forEach {
            val user = it
            it.takenKits.keys.filter { getKit(it) == null }.forEach {
                user.takenKits.remove(it)
                user.updateEntity()
            }
        }
        plugin.logger.info("Invalid kits has been cleaned!")
    }
}