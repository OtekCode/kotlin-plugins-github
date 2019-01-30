package pl.otekplay.history

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("HistoryPlugin",["DatabasePlugin"])
class HistoryPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: HistoryConfig
    lateinit var manager: HistoryManager
    lateinit var menu: HistoryMenu

    override fun onEnable() {
        config= loadConfig(HistoryConfig::class)
        manager = HistoryManager(this)
        menu = HistoryMenu(this)
        registerCommand(HistoryCommand(this))
        HistoryAPI.plugin = this
    }
}