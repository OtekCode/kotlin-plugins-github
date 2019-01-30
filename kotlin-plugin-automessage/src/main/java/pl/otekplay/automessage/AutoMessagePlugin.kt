package pl.otekplay.automessage

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.logging.Logger

@PluginAnnotation(name = "AutoMessage",dependency = [])
class AutoMessagePlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: AutoMessageConfig

    override fun onEnable() {
        config = loadConfig(AutoMessageConfig::class)
        registerCommand(AutoMessageCommand(this))
        taskTimerAsync(object : BukkitRunnable() {
            var ite = config.messages.messages.iterator()
            override fun run() {
                if (config.messages.messages.isEmpty()) return
                if (! ite.hasNext()) ite = config.messages.messages.iterator()
                ite.next().forEach { Bukkit.broadcastMessage(it) }
            }

        }, 20 * config.secondsBeetwenMessages)
    }
}

