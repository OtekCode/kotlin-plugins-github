package pl.otekplay.generator

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "GeneratorPlugin", dependency = ["DatabasePlugin", "GuildPlugin"])
class GeneratorPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader,
        annotation,
        logger) {


    lateinit var config: GeneratorConfig
    lateinit var manager: GeneratorManager

    override fun onEnable() {
        config = loadConfig(GeneratorConfig::class)
        manager = GeneratorManager(this)
        registerListener(GeneratorListener(this))
        registerCommand(GeneratorCommand(this))
        taskTimerSync(GeneratorTask(this), config.intervalCheckGenerators)

    }
}