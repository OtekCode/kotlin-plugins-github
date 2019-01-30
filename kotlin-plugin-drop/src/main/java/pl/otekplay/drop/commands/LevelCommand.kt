package pl.otekplay.drop.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.drop.DropPlugin
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep

@CommandAlias("level")
@CommandPermission("command.level")
class LevelCommand(val plugin: DropPlugin) : PluginCommand() {


//    @Subcommand("need")
//    @CommandPermission("command.level.need")
//    fun onCommandLevelNeed(player: Player) {
//        val user = plugin.manager.getUser(player.uniqueId) ?: return
//        val needToNextLevel = plugin.manager.getNeedToNextLevel(user)
//        return if (needToNextLevel == -1)
//            player.sendMessage(plugin.config.messageYouGotMaxLevelDrop)
//        else
//            player.sendMessage(plugin.config.messageYouNeedAmountExpToLevel.rep("%exp%", needToNextLevel.toString()))
//
//    }

    @Subcommand("info")
    @CommandPermission("command.level.info")
    fun onCommandLevelInfo(player: Player) {
        val user = plugin.manager.getUser(player.uniqueId) ?: return
        val needToNextLevel = plugin.manager.getNeedToNextLevel(user)
        player.sendMessage(plugin.config.messages.dropLevelInfo.rep("%level%",user.level.toString()) + " ${plugin.config.messages.youNeedAmountExpToLevel.rep("%exp%", needToNextLevel.toString())}")
    }


}