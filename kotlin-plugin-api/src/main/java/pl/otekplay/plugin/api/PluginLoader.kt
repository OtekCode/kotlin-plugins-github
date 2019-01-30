package pl.otekplay.plugin.api

import com.comphenix.protocol.events.PacketListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

interface PluginLoader {
    val loaderPlugin: JavaPlugin
    val plugins: Set<Plugin>
    val loaded: Set<PluginFile>
    val folder: File
    val gson: Gson

    fun loadPlugin(file: File)

    fun enablePlugin(name: String)

    fun disablePlugin(name: String)

    fun isEnabled(name: String): Boolean

    fun isLoaded(name: String): Boolean

    fun registerListener(plugin: Plugin, listener: Listener)

    fun unregisterListener(plugin: Plugin, listener: Listener)

    fun registerPacketListener(plugin: Plugin, listener: PacketListener)

    fun unregisterPacketListener(plugin: Plugin, listener: PacketListener)

    fun registeredPacketListeners(plugin: Plugin): Set<PacketListener>

    fun registeredListeners(plugin: Plugin): Set<Listener>

    fun registerCommand(plugin: Plugin, command: PluginCommand)

    fun unregisterCommand(plugin: Plugin, command: PluginCommand)

    fun registeredCommands(plugin: Plugin): Set<PluginCommand>


    fun task(plugin: Plugin, task: BukkitRunnable, async: Boolean = false) {
        when (async) {
            true -> task.runTaskAsynchronously(loaderPlugin)
            false -> task.runTask(loaderPlugin)
        }
    }

    fun taskLater(plugin: Plugin, task: BukkitRunnable, async: Boolean = false, time: Long = 1) {
        when (async) {
            true -> task.runTaskLaterAsynchronously(loaderPlugin, time)
            false -> task.runTaskLater(loaderPlugin, time)
        }
    }

    fun taskTimer(plugin: Plugin, task: BukkitRunnable, async: Boolean = false, time: Long = 1) {

        when (async) {
            true -> task.runTaskTimerAsynchronously(loaderPlugin, time, time)
            false -> task.runTaskTimer(loaderPlugin, time, time)
        }
    }

    fun saveConfig(plugin: Plugin, configuration: PluginConfiguration) = saveConfig(File(plugin.folder, "${configuration.javaClass.simpleName}.json"), configuration)

    private fun saveConfig(configFile: File, configuration: PluginConfiguration) {
        loaderPlugin.logger.info("Saving config: ${configFile.name}")
        val json = gson.toJson(configuration)
        loaderPlugin.logger.info(json)
        Files.write(configFile.toPath(), json.toByteArray(Charsets.UTF_8))
    }


    fun <T> loadConfig(plugin: Plugin, cl: KClass<T>): T where T : PluginConfiguration = loadConfig(plugin, cl, cl.java.simpleName)

    fun <T> loadConfig(plugin: Plugin, cl: KClass<T>, name: String): T where T : PluginConfiguration = loadConfig(plugin, cl, cl.createInstance(), cl.java.simpleName)

    fun <T> loadConfig(plugin: Plugin, cl: KClass<T>, obj: T, name: String): T where T : PluginConfiguration {
        loaderPlugin.logger.info("${plugin.name} loading config $name ${obj.javaClass.simpleName}")
        val file = File(plugin.folder, "$name.json")
        loaderPlugin.logger.info("${plugin.name} config $name no exist, creating default...")
        if (!file.exists()) saveConfig(file, obj)
        loaderPlugin.logger.info("${plugin.name} config $name path: ${file.path}")
        val stringConfig = file.readText(Charsets.UTF_8)
        loaderPlugin.logger.info("${plugin.name} config $name text: $stringConfig")
        val fixed = ChatColor.translateAlternateColorCodes('&',stringConfig)
        loaderPlugin.logger.info("${plugin.name} config $name fixedText: $stringConfig")
        return gson.fromJson(fixed, cl.java)
    }


}
data class Test(val name:String = "elo")

fun main(args: Array<String>) {
    val file = File("test.json")
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson(Test())
    Files.write(file.toPath(), json.toByteArray())
    println(file.readText())
}