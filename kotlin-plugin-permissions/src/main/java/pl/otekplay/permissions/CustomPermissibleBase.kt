package pl.otekplay.permissions

import org.bukkit.entity.Player
import org.bukkit.permissions.PermissibleBase
import org.bukkit.permissions.Permission

class CustomPermissibleBase(private val player :Player) : PermissibleBase(player) {
    override fun hasPermission(inName: String): Boolean = PermissionsAPI.checkPermission(player.uniqueId, inName)

    override fun hasPermission(perm: Permission): Boolean = PermissionsAPI.checkPermission(player.uniqueId, perm.name)
}