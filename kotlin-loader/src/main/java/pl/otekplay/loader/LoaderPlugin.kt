package pl.otekplay.loader

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import pl.otekplay.menu.api.MenuListener
import java.util.*


class LoaderPlugin : JavaPlugin() {
    private lateinit var loader: KotlinLoader

    override fun onEnable() {
        logger.info("Enabling loader plugin...")
        saveConfig()
        Bukkit.getPluginManager().registerEvents(MenuListener(), this)
        loader = KotlinLoader(this)

    }



    override fun onDisable() {
        loader.close()
        loader.commandManager.unregisterCommands()
        HandlerList.unregisterAll(this)
    }
}

