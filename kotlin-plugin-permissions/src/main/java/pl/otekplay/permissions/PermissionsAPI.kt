package pl.otekplay.permissions

import java.util.*

object PermissionsAPI {
    internal lateinit var plugin: PermissionsPlugin

    fun getUser(uniqueId :UUID) : PermissionsUser? = plugin.manager.getUser(uniqueId)

    private fun getGroup(name :String) : PermissionsGroup? = plugin.manager.getGroup(name)

    fun checkPermission(uniqueId: UUID, permission :String) :Boolean {
        val user = getUser(uniqueId) ?: return false
        if (user.permissions.contains(permission) || user.superuser) return true
        val group = getGroup(user.group)?: plugin.manager.getDefaultGroup()
        return group.permissions.contains(permission)
    }
}