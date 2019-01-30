package pl.otekplay.spawn

import pl.otekplay.plugin.api.PluginConfiguration

class SpawnConfig:PluginConfiguration{
    val messages = SpawnMessages()
    val spawnLocationX = 0.0
    val spawnLocationY = 70.0
    val spawnLocationZ = 0.0
    val spawnLocationYaw = 0F
    val spawnLocationPitch = 0F
    val cuboidProtectionRadius = 150
    val pvpProtectionRadius = 100
    val spawnTeleportDelay = 10000L
    val permissionBypassBuild = "spawn.build.bypass"
    val permissionAllowDropItemOnSpawn = "spawn.drop"
    val permissionCheckTerrainInfo = "spawn.check"

}