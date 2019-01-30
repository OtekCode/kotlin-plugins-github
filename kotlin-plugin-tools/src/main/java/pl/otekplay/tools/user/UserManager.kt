package pl.otekplay.tools.user

import pl.otekplay.database.DatabaseAPI
import pl.otekplay.tab.TabAPI
import pl.otekplay.tools.ToolsManager
import pl.otekplay.tools.ToolsPlugin
import java.util.*

class UserManager(plugin: ToolsPlugin) : ToolsManager(plugin) {
    override fun registerVariables() {
        TabAPI.registerVariable("users") { p -> plugin.userManager._users.size.toString() }
    }

    private val _users = HashMap<UUID, User>()
    val users get() = _users.values

    init {
        DatabaseAPI.loadAll<User>("players") {
            it.forEach { _users[it.uniqueId] = it }
            plugin.logger.info("Loaded ${_users.size} players.")
        }
    }

    fun getUser(uniqueId: UUID) = _users[uniqueId]

    fun createUser(uniqueId: UUID, nickname: String) = User(uniqueId, nickname, System.currentTimeMillis(), System.currentTimeMillis(), 0).apply { insertEntity() }.also { _users[it.uniqueId] = it }

    override fun registerCommands() {

    }

    override fun registerListeners() {
        plugin.registerListener(UserListener(this))
    }
}