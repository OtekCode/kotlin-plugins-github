package pl.otekplay.tools.config

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration

class ToolsConfig : PluginConfiguration {
    val messages = ToolsMessages()
    val explosionStartHour = "10:00"
    val explosionEndHour = "22:00"
    val permissionVanishBypass = "vanish.bypass"
    val suffixVanish = "[Vanish]"
    val permissionManipulateInventoryPlayer = "inventory.change"
    val permissionManipulateEnderchestPlayer = "inventory.enderchest"
    val formatHelpop = "[POMOC] %name% : %message%"
    val permissionSeeHelpop = "helpop.see"
    val explodeChances = mapOf(
            Pair(Material.OBSIDIAN.id,20.0)
    )
}