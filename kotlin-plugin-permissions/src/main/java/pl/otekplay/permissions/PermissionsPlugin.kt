package pl.otekplay.permissions

import pl.otekplay.permissions.commands.GroupCommand
import pl.otekplay.permissions.commands.ReloadCommand
import pl.otekplay.permissions.commands.SuperCommand
import pl.otekplay.permissions.commands.UserCommand
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "PermissionsPlugin", dependency = [ "DatabasePlugin" ])
class PermissionsPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var manager : PermissionsManager
    lateinit var config : PermissionsConfig

    override fun onEnable() {
        config = loadConfig(PermissionsConfig::class)
        manager = PermissionsManager(this)
        PermissionsAPI.plugin = this

        registerListener(PermissionListener(this))

        registerCommand(UserCommand(this))
        registerCommand(GroupCommand(this))
        registerCommand(ReloadCommand(this))
        registerCommand(SuperCommand(this))
    }

    fun reloadConfig(){
        config = loadConfig(PermissionsConfig::class)
        manager.loadGroups()
    }
}