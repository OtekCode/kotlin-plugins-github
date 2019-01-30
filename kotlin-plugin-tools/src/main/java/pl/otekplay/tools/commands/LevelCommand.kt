package pl.otekplay.tools.commands

import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.tools.ToolsCommand
import pl.otekplay.tools.ToolsPlugin

@CommandAlias("level")
@CommandPermission("command.exp")
class LevelCommand(plugin: ToolsPlugin) : ToolsCommand(plugin) {

    @Subcommand("give")
    @CommandPermission("command.exp.level")
    @Syntax("[gracz] <poziom>")
    @CommandCompletion("@players")
    fun onCommandGive(sender: Player, @Optional @Flags("other") other: Player?,level: Int) {
        val target = other ?: sender
        target.level = target.level + level
        sender.sendMessage(config.messages.youGivedLevelToPlayer.rep("%level%",level.toString()).rep("%name%",target.name))
    }

    @Subcommand("ustaw|set")
    @CommandPermission("command.exp.set")
    @Syntax("[gracz] <exp>")
    @CommandCompletion("@players")
    fun onCommandSet(sender: Player, @Optional @Flags("other") other: Player?,level: Int) {
        val target = other ?: sender
        target.level = level
        sender.sendMessage(config.messages.youSetLevelPlayer.rep("%level%",level.toString()).rep("%name%",target.name))
    }

}