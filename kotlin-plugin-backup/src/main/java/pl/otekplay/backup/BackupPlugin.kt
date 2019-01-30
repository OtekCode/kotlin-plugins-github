package pl.otekplay.backup

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger


@PluginAnnotation(name = "BackupPlugin",dependency = ["DatabasePlugin"])
class BackupPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config:BackupConfig
    lateinit var manager:BackupManager
    lateinit var menu:BackupMenu
    override fun onEnable() {
        config = loadConfig(BackupConfig::class)
        manager = BackupManager(this)
        menu = BackupMenu(this)
        taskTimerAsync(BackupTask(this),config.backupAutoSaveTime)
        registerListener(BackupListener(this))
        registerCommand(BackupCommands(this))
    }


    fun reloadConfig(){
        config = loadConfig(BackupConfig::class)

    }
}