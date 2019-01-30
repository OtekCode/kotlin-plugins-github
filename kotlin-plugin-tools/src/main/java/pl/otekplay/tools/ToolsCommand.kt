package pl.otekplay.tools

import pl.otekplay.plugin.api.PluginCommand

abstract class ToolsCommand(val plugin : ToolsPlugin) : PluginCommand(){
    val config = plugin.config
}