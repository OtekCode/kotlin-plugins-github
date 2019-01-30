package pl.otekplay.plugin.api

import com.comphenix.protocol.events.PacketListener
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.util.logging.Logger
import kotlin.reflect.KClass


open class Plugin(val pluginLoader: PluginLoader, annotation: PluginAnnotation, val logger: Logger) {
    val name = annotation.name
    val dependency = annotation.dependency
    val folder = File(pluginLoader.folder, name)
    val gson get() = pluginLoader.gson


    open fun onEnable() {}

    open fun onDisable() {}

    fun registerCommand(command: PluginCommand) = pluginLoader.registerCommand(this, command)

    fun unregisterCommand(command: PluginCommand) = pluginLoader.unregisterCommand(this, command)

    fun registerListener(listener: Listener) = pluginLoader.registerListener(this, listener)

    fun registerPacketListener(listener: PacketListener) = pluginLoader.registerPacketListener(this, listener)

    fun unregisterPacketListener(listener: PacketListener) = pluginLoader.unregisterPacketListener(this, listener)

    fun unregisterListener(listener: Listener) = pluginLoader.unregisterListener(this, listener)


    fun runSync(runnable: Runnable) {
        if (Bukkit.isPrimaryThread()) return runnable.run()
        taskSync(object : BukkitRunnable() {
            override fun run() {
                runnable.run()
            }
        })
    }

    private fun taskSync(bukkitRunnable: BukkitRunnable) = pluginLoader.task(this, bukkitRunnable, false)

    fun taskAsync(bukkitRunnable: BukkitRunnable) = pluginLoader.task(this, bukkitRunnable, true)

    fun taskLaterSync(bukkitRunnable: BukkitRunnable, time: Long) = pluginLoader.taskLater(this, bukkitRunnable, false, time)

    fun taskLaterAsync(bukkitRunnable: BukkitRunnable, time: Long) = pluginLoader.taskLater(this, bukkitRunnable, true, time)

    fun taskTimerSync(bukkitRunnable: BukkitRunnable, repeat: Long) = pluginLoader.taskTimer(this, bukkitRunnable, false, repeat)

    fun taskTimerAsync(bukkitRunnable: BukkitRunnable, repeat: Long) = pluginLoader.taskTimer(this, bukkitRunnable, true, repeat)

    fun <T> loadConfig(cl: KClass<T>): T where T : PluginConfiguration = this.pluginLoader.loadConfig(this, cl)

    fun <T> loadConfig(cl: KClass<T>, name: String): T where T : PluginConfiguration = this.pluginLoader.loadConfig(this, cl, name)

    fun <T> loadConfig(cl: KClass<T>, any: T, name: String): T where T : PluginConfiguration = this.pluginLoader.loadConfig(this, cl, any, name)

    fun saveConfig(config: PluginConfiguration) = pluginLoader.saveConfig(this, config)

    fun callEvent(event: Event) = Bukkit.getPluginManager().callEvent(event)


}