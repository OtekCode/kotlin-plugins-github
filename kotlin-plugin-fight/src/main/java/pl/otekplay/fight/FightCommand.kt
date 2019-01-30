package pl.otekplay.fight

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import java.util.*

@CommandAlias("fight")
@CommandPermission("command.fight")
class FightCommand(val plugin: FightPlugin):PluginCommand() {

    @Subcommand("list")
    @CommandPermission("command.fight.list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.listPlayersWhileFighting.rep("%players%", Arrays.deepToString(plugin.manager.validFights.map { Bukkit.getOfflinePlayer(it.uniqueId).name }.toTypedArray())))
    }

    @Subcommand("reload")
    @CommandPermission("command.fight.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}