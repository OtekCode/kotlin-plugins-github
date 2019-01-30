package pl.otekplay.teleporter

import pl.otekplay.plugin.api.PluginConfiguration

class TeleportConfig : PluginConfiguration {
    val messages = TeleportMessages()
    val teleportTaskScheduleTime = 1L
    val teleportAskHereTime = 10000L
    val teleportAskToTime = 10000L
    val permissionInstaTeleport = "teleporter.instant.teleport"

}