package pl.otekplay.spawn

import org.bukkit.Bukkit
import org.bukkit.Location
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.plugin.util.Locations
import java.util.logging.Logger

@PluginAnnotation(name = "SpawnPlugin",dependency = ["TeleportPlugin"])
class SpawnPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: SpawnConfig
    lateinit var spawnLocation: Location

    override fun onEnable() {
        config = loadConfig(SpawnConfig::class)
        spawnLocation = Location(
                Bukkit.getWorlds()[0],
                config.spawnLocationX,
                config.spawnLocationY,
                config.spawnLocationZ,
                config.spawnLocationYaw,
                config.spawnLocationPitch
        )
        registerListener(SpawnListener(this))
        registerCommand(SpawnCommands(this))
        SpawnAPI.plugin = this
    }

    fun reloadConfig(){
        config = loadConfig(SpawnConfig::class)
        spawnLocation = Location(
                Bukkit.getWorlds()[0],
                config.spawnLocationX,
                config.spawnLocationY,
                config.spawnLocationZ,
                config.spawnLocationYaw,
                config.spawnLocationPitch
        )
    }

    fun isCuboidProtection(location: Location) = Locations.isInLocation(spawnLocation,config.cuboidProtectionRadius,location)

    fun isPvPProtection(location: Location) = Locations.isInLocation(spawnLocation,config.pvpProtectionRadius,location)
}