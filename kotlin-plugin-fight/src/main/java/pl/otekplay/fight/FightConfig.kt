package pl.otekplay.fight

import pl.otekplay.plugin.api.PluginConfiguration

class FightConfig : PluginConfiguration {
    val validAttackerTime = 30000L
    val messages = FightMessages()
    val permissionCombatDisabledBypass = "fight.bypass"
    val speedPushAwayFromSpawn = 2.0

}