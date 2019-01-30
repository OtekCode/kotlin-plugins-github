package pl.otekplay.loader

import co.aikar.commands.ExceptionHandler
import co.aikar.commands.PaperCommandManager
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import pl.otekplay.plugin.api.*
import java.io.File
import java.net.URLClassLoader
import java.util.*
import java.util.jar.JarFile
import java.util.logging.Logger
import kotlin.collections.HashSet
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmName

class KotlinLoader(private val _loaderPlugin: LoaderPlugin) : PluginLoader {
    private val _gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    internal val commandManager = PaperCommandManager(_loaderPlugin)
    private val _folder = File(loaderPlugin.dataFolder.parentFile.parentFile, "kotlin-plugins")
    private val _plugins = HashSet<Plugin>()
    private val _loaded = HashSet<PluginFile>()
    private val _registeredCommands = HashMap<Plugin, Set<PluginCommand>>()
    private val _registeredListeners = HashMap<Plugin, Set<Listener>>()
    private val _registeredPacketListeners = HashMap<Plugin, Set<PacketListener>>()
    private val classLoader = KotlinClassLoader(this.javaClass.classLoader)
    private val logger = _loaderPlugin.logger
    override val loaderPlugin: JavaPlugin get() = _loaderPlugin
    override val plugins: Set<Plugin> get() = Collections.unmodifiableSet(_plugins)
    override val loaded: Set<PluginFile> get() = Collections.unmodifiableSet(_loaded)
    override val folder: File get() = _folder
    override val gson: Gson get() = _gson


    init {
        initCommands()
        buildFolder()
        loadPlugins()
        enablingPlugins()

    }

    fun close() {
        plugins.map { it.name }
                .forEach {
            disablePlugin(it)
        }
        _plugins.clear()
        _loaded.clear()
    }

    private fun initCommands() {
        commandManager.defaultExceptionHandler = ExceptionHandler { command, _, sender, _, _ ->
            sender.sendMessage("Wystapil problem z komenda ${command.name}, zglos to jak najszybciej do admina :)")
            true
        }
    }

    private fun buildFolder() {
        logger.info("Building folder for loader...")
        if (! _folder.exists()) {
            logger.info("Creating loader folder!")
            _folder.mkdir()
            return
        }
        logger.info("Folder detected, detected files: " + folder.listFiles().size)
    }

    private fun loadPlugins() {
        logger.info("Loading all jars from loader folder...")
        folder.listFiles()
                .filter { it.name.endsWith(".jar") }
                .forEach { loadPlugin(it) }
    }

    private fun enablingPlugins() {
        if (_loaded.size == 0) return logger.info("There no loaded plugins :( Feel useless as loader ;v")

        logger.info("Loaded plugins: " + _plugins.size)
        var plugin = 0
        _loaded.forEach {
            plugin ++
            it.annotation.apply {
                logger.info("$plugin)$name Dependency: ${Arrays.deepToString(dependency)}")
            }
        }
        val iterator = _loaded.iterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (! isEnabled(it.annotation.name)) enablePlugin(it.annotation.name)
        }

    }

    override fun loadPlugin(file: File) {
        logger.info("File ${file.name} looks like jar, checking...")
        val url = file.toURI().toURL()
        val loader = URLClassLoader(Array(1) { url }, javaClass.classLoader)
        try {
            val jar = JarFile(file)
            logger.info("File ${file.name} is jar file! Scanning for main class...")
            val entries = jar.entries()
            while (entries.hasMoreElements()) {
                val element = entries.nextElement()
                if (element.name.contains("$")) continue
                if (! element.name.endsWith(".class")) continue
                val className = element
                        .name
                        .substring(0, element.name.length - 6)
                        .replace("/", ".")
                try {
                    //logger.info("Checking classname: $className")
                    val cl = loader.loadClass(className).kotlin
                    if (Plugin::class.isSubclassOf(cl)) continue
                    if (! cl.annotations.any { it.annotationClass == PluginAnnotation::class }) continue
                    if (Plugin::class.isSubclassOf(cl)) continue
                    val annotation = cl.annotations.find { it.annotationClass == PluginAnnotation::class } as PluginAnnotation
                    _loaded.add(PluginFile(annotation, cl.jvmName, url))
                    logger.info("Loaded plugin ${annotation.name} with success!")
                    return
                } catch (e: Throwable) {
                    continue
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            loader.close()
        }
        logger.info("Plugin ${file.name.replace(".jar", "")} can't be loaded.")
    }

    override fun enablePlugin(name: String) {
        if (! isLoaded(name)) return logger.warning("Can't enable plugin $name is not loaded.")
        if (isEnabled(name)) return logger.warning("Can't enable plugin $name is already enabled!")
        val file = _loaded.single { it.annotation.name.equals(name, true) }
        file.annotation.dependency.forEach {
            if (! isEnabled(it)) {
                logger.warning("Plugin ${file.annotation.name} has missing dependency $it trying to force enable.")
                enablePlugin(it)
            }
        }
        val pluginLoader = classLoader.buildPluginLoader(file.url)
        val loadClass = pluginLoader.loadClass(file.main)
        val instance = loadClass
                .getConstructor(PluginLoader::class.java, PluginAnnotation::class.java, Logger::class.java)
                .newInstance(this, file.annotation, PluginLogger(file.annotation, logger))
                as Plugin
        if (! instance.folder.exists()) instance.folder.mkdir()
        _plugins.add(instance)
        _registeredListeners[instance] = HashSet()
        _registeredCommands[instance] = HashSet()
        _registeredPacketListeners[instance] = HashSet()
        logger.info("Enabled plugin $name")
        try {
            instance.onEnable()
        } catch (e: Throwable) {
            e.printStackTrace()
            return
        }
    }

    override fun registerCommand(plugin: Plugin, command: PluginCommand) {
        this.logger.info("Plugin ${plugin.name} registering command class ${command.javaClass.simpleName} ...")
        this.commandManager.registerCommand(command)
        this._registeredCommands[plugin] !!.plus(command)
        this.logger.info("Plugin ${plugin.name} registered command ${command.name}.")

    }


    override fun unregisterCommand(plugin: Plugin, command: PluginCommand) {
        this.logger.info("Plugin ${plugin.name} unregistering command ${command.name} ...")
        this.commandManager.unregisterCommand(command)
        this._registeredCommands[plugin] !!.minus(command)
       this.logger.info("Plugin ${plugin.name} unregister command ${command.name} with success!")

    }

    override fun registeredCommands(plugin: Plugin): Set<PluginCommand> {
        return this._registeredCommands[plugin] !!
    }

    override fun disablePlugin(name: String) {
        if (! isLoaded(name)) return logger.info("Can't disable plugin $name is not loaded!")

        if (! isEnabled(name)) {
            logger.info(_plugins.size.toString())
            logger.info("Can't disable plugin $name is not enabled!")
            return
        }
        logger.info("Disabling plugin $name")
        logger.info("Checking if plugin $name is dependency...")
        _plugins.filter { it.dependency.contains(name) }
                .forEach {
                    logger.info("Plugin " + it.name + " have dependecy plugin " + name + " force disabling...")
                    disablePlugin(it.name)
                }
        if (! _plugins.none { it.dependency.contains(name) }) return logger.info("Plugin $name can't be disabled, still some plugin is not disabled with dependency.")
        val plugin = _plugins.single { it.name.contains(name, true) }
        logger.info("Invoking disable plugin $name")
        plugin.onDisable()
        logger.info("Unregistering all packet listeners plugin $name")
        registeredPacketListeners(plugin).forEach { unregisterPacketListener(plugin,it) }
        logger.info("Unregistering all listeners plugin $name")
        registeredListeners(plugin).forEach { unregisterListener(plugin, it) }
        logger.info("Unregistering all commands plugin $name")
        registeredCommands(plugin).forEach { unregisterCommand(plugin, it) }
        logger.info("Unloading pluginFile plugin $name")
        _loaded.remove(_loaded.single { it.annotation.name.equals(name, true) })
        logger.info("Removing plugin $name from list!")
        _plugins.remove(plugin)
        logger.info("Unloading class loader plugin $plugin")
        (plugin.javaClass.classLoader as PluginClassLoader).unload()
        logger.info("Plugin $name has been disabled!")
    }

    override fun isEnabled(name: String) = _plugins.any { it.name.equals(name, true) }

    override fun isLoaded(name: String) = _loaded.any { it.annotation.name.equals(name, true) }

    override fun registerListener(plugin: Plugin, listener: Listener) {
        loaderPlugin.logger.info("Plugin " + plugin.name + " registering listener " + listener.javaClass.simpleName)
        loaderPlugin.server.pluginManager.registerEvents(listener, loaderPlugin)
        _registeredListeners[plugin] !!.plus(listener)
    }

    override fun unregisterListener(plugin: Plugin, listener: Listener) {
        loaderPlugin.logger.info("Plugin " + plugin.name + " unregistered listener " + listener.javaClass.simpleName)
        HandlerList.unregisterAll(listener)
        _registeredListeners[plugin] !!.minus(listener)
    }

    override fun registerPacketListener(plugin: Plugin, listener: PacketListener) {
        loaderPlugin.logger.info("Plugin " + plugin.name + " registering packet listener " + listener.javaClass.simpleName)
        ProtocolLibrary.getProtocolManager().addPacketListener(listener)
        _registeredPacketListeners[plugin] !!.plus(listener)
    }

    override fun unregisterPacketListener(plugin: Plugin, listener: PacketListener) {
        loaderPlugin.logger.info("Plugin " + plugin.name + " unregistered packet listener " + listener.javaClass.simpleName)
        ProtocolLibrary.getProtocolManager().removePacketListener(listener)
        _registeredPacketListeners[plugin] !!.minus(listener)
    }


    override fun registeredListeners(plugin: Plugin) = _registeredListeners[plugin] ?: Collections.emptySet()

    override fun registeredPacketListeners(plugin: Plugin) = _registeredPacketListeners[plugin] ?: Collections.emptySet()

}