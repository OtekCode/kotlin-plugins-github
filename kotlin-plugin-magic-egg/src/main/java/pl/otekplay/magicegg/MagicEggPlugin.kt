package pl.otekplay.magicegg

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger


@PluginAnnotation("MagicEggPlugin", [])
class MagicEggPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: MagicEggConfig
    lateinit var manager: MagicEggManager
    override fun onEnable() {
        config = loadConfig(MagicEggConfig::class)
        manager = MagicEggManager(this)
        registerListener(MagicEggListener(this))
        registerCommand(MagicEggCommand(this))

    }


    fun reloadConfig(){
        config = loadConfig(MagicEggConfig::class)
        manager.loadEggs()
    }
}