package pl.otekplay.tools

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.tools.commands.*
import pl.otekplay.tools.config.ToolsConfig
import pl.otekplay.tools.listeners.AnvilListener
import pl.otekplay.tools.listeners.InfoListener
import pl.otekplay.tools.listeners.InventoryListener
import pl.otekplay.tools.system.explode.ExplodeCommand
import pl.otekplay.tools.system.explode.ExplodeManager
import pl.otekplay.tools.user.UserManager
import pl.otekplay.tools.system.vanish.VanishManager
import java.util.logging.Logger


@PluginAnnotation(name = "ToolsPlugin", dependency = ["TabPlugin", "TagPlugin", "DatabasePlugin", "PermissionPlugin"])
class ToolsPlugin(pluginLoader: PluginLoader,
                  annotation: PluginAnnotation,
                  logger: Logger
) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: ToolsConfig
    lateinit var explodeManager: ExplodeManager
    lateinit var vanishManager: VanishManager
    lateinit var userManager: UserManager

    override fun onEnable() {
        config = loadConfig(ToolsConfig::class)
        explodeManager = ExplodeManager(this).apply { init() }
        vanishManager = VanishManager(this).apply { init() }
        userManager = UserManager(this).apply { init() }
        logger.info("Registering default listeners...")
        registerListener(InventoryListener(this))
        registerListener(AnvilListener(this))
        registerListener(InfoListener(this))
        logger.info("Registering default commands...")
        registerCommand(ExplodeCommand(this))
        registerCommand(FlyCommand(this))
        registerCommand(GameModeCommand(this))
        registerCommand(ReloadCommand(this))
        registerCommand(EnderchestCommand(this))
        registerCommand(WorkbenchCommand(this))
        registerCommand(HealCommand(this))
        registerCommand(FoodCommand(this))
        registerCommand(OpenCommand(this))
        registerCommand(CleanCommand(this))
        registerCommand(LevelCommand(this))
        registerCommand(SeenCommand(this))
        registerCommand(WhoisCommand(this))
        registerCommand(PlayerCommand(this))
        registerCommand(HelpopCommand(this))
    }


    fun reloadConfig() {
        config = loadConfig(ToolsConfig::class)
    }


}