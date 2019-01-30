package pl.otekplay.tag

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger


@PluginAnnotation(name = "TagPlugin", dependency = [])
class TagPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: TagConfig
    lateinit var manager: TagManager
    private lateinit var task: TagTask
    override fun onEnable() {
        config = loadConfig(TagConfig::class)
        manager = TagManager(this)
        task = TagTask(this)
        registerCommand(TagCommand(this))
        registerListener(TagListener(this))
        taskTimerSync(TagTask(this),config.tagTimer)
        logger.info("TagAPI has been initialized!")
        TagAPI.plugin = this
    }

    fun reloadConfig(){
        task.cancel()
        config = loadConfig(TagConfig::class)
        taskTimerSync(task,config.tagTimer)
    }

}