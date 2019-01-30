package pl.otekplay.tools.system.explode

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin


class ExplodeCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @CommandAlias("explode")
    @CommandPermission("command.explode")
    fun onCommandInfo(sender: CommandSender) {
        sender.sendMessage(config.messages.explodeEnabledBeetwen
                .rep("%end%", config.explosionEndHour)
                .rep("%start%", config.explosionStartHour)
        )
    }

}