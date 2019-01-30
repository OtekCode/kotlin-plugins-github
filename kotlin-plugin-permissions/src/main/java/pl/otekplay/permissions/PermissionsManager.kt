package pl.otekplay.permissions

import org.bukkit.entity.Player
import org.bukkit.permissions.PermissibleBase
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.plugin.util.Files
import pl.otekplay.plugin.util.Reflections
import java.io.File
import java.io.FileReader
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.nio.charset.StandardCharsets
import java.util.*

class PermissionsManager(private val plugin: PermissionsPlugin) {
    val users = HashMap<UUID, PermissionsUser>()
    val groups = HashMap<String, PermissionsGroup>()
    private val default: PermissionsGroup by lazy { PermissionsGroup("gracz", true, arrayListOf("super.perm.1", "super.perm.2", "super.perm.3")) }

    init {
        saveDefaultGroup()
        loadGroups()
        loadUsers()
    }

    fun getDefaultGroup() = groups.values.single { it.default }


    private fun saveDefaultGroup() {
        val defaultFile = File(plugin.folder, default.name)
        if (defaultFile.exists()) return
        Files.saveJson(File(plugin.folder, default.name), plugin.pluginLoader.gson.toJson(default))
    }

    private fun loadUsers() {
        users.clear()
        DatabaseAPI.loadAll<PermissionsUser>("permissions_users") {
            it.forEach { users[it.uniqueId] = it }
            val default = getDefaultGroup()
            it.filter { getGroup(it.group) == null }.forEach { it.group = default.name }
            plugin.logger.info("Loaded ${users.size} permissions users!")
        }
    }

    fun loadGroups() {
        groups.clear()
        plugin.folder
                .listFiles()
                .filter { !it.name.contains("Config") }
                .forEach {
                    try {
                        plugin.logger.info("Loading ${it.name} group file...")
                        val json = plugin.pluginLoader.gson.fromJson(it.readText(), PermissionsGroup::class.java)
                        if (!json.name.contains("example")) groups[json.name.toLowerCase()] = json
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        plugin.logger.info("Loaded " + groups.size + " permission groups.")
        if (groups.values.any { it.default }) return
        groups[default.name] = default
        plugin.logger.info("Default group has been added cuz groups can't be empty!")
    }

    fun getUser(uniqueId: UUID) = users[uniqueId]

    fun getGroup(name: String) = groups[name.toLowerCase()]

    fun createUser(uniqueId: UUID) = PermissionsUser(uniqueId, getDefaultGroup().name)
            .also { users[it.uniqueId] = it }
            .also { it.insertEntity() }

    fun inject(player: Player, permissible: PermissibleBase) {
        val field = Reflections.obcClass("entity.CraftHumanEntity").getDeclaredField("perm")
        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.isAccessible = true
        field.set(player, permissible)
    }


}