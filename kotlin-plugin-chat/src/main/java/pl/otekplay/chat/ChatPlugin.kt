package pl.otekplay.chat

import pl.otekplay.chat.commands.BroadcastCommand
import pl.otekplay.chat.commands.ChatAdminCommand
import pl.otekplay.chat.commands.MessageCommand
import pl.otekplay.chat.config.ChatConfig
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "ChatPlugin", dependency = ["DatabasePlugin"])
class ChatPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: ChatConfig

    override fun onEnable() {
        config = loadConfig(ChatConfig::class)
        registerListener(ChatListener(this))
        registerCommand(ChatAdminCommand(this))
        registerCommand(BroadcastCommand(this))
        registerCommand(MessageCommand(this))
    }


     fun reloadConfig(){
        config = loadConfig(ChatConfig::class)
    }
}