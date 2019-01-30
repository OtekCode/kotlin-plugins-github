package pl.otekplay.database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Location
import pl.otekplay.database.adapters.LocationTypeAdapter
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "DatabasePlugin", dependency = [])
class DatabasePlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config : DatabaseConfig

    lateinit var databaseSeralizer: Gson
    lateinit var database : Database

    override fun onEnable() {
        logger.info("Loading datasource config...")
        config = loadConfig(DatabaseConfig::class)
        databaseSeralizer = GsonBuilder()
                .registerTypeAdapter(Location::class.java, LocationTypeAdapter())
                .create()
        try {
            logger.info("Connecting to mongodb cluster...")
            logger.info("Host: "+config.url)
            database = Database(this)
        } catch (e: Exception) {
            logger.warning("Can't connect to database, disabling plugin.")
            pluginLoader.disablePlugin(name)
            return
        }
        DatabaseAPI.plugin = this
    }
}



