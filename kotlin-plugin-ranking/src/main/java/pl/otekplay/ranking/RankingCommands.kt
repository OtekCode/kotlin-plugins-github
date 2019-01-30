package pl.otekplay.ranking

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep


@CommandAlias("ranking")
@CommandPermission("command.ranking")
class RankingCommands(private val plugin: RankingPlugin) : PluginCommand() {

    @Subcommand("top punkty|top points")
    @CommandPermission("command.ranking.top.points")
    fun onCommandTopPoints(player: Player) {
        plugin.manager.openTopMenu(player, plugin.config.menuTopPoints, plugin.manager.rankings)
    }

    @Subcommand("top asysty|top assists")
    @CommandPermission("command.ranking.top.assists")
    fun onCommandTopAssists(player: Player) {
        plugin.manager.openTopMenu(player, plugin.config.menuTopAssists, plugin.manager.rankingsByAssists)
    }

    @Subcommand("top zabojstwa|top kills")
    @CommandPermission("command.ranking.top.kills")
    fun onCommandTopKills(player: Player) {
        plugin.manager.openTopMenu(player, plugin.config.menuTopKills, plugin.manager.rankingsByKills)
    }

    @Subcommand("top zgony|top deaths")
    @CommandPermission("command.ranking.top.deaths")
    fun onCommandTopDeaths(player: Player) {
        plugin.manager.openTopMenu(player, plugin.config.menuTopDeaths, plugin.manager.rankingsByDeaths)
    }

    @Subcommand("info")
    @CommandPermission("command.ranking.info")
    @Syntax("<gracz>")
    fun onCommandRankingInfo(sender: CommandSender, player: OfflinePlayer) {
        val rank = plugin.manager.getRanking(player.uniqueId)
                ?: return
        val messages = arrayListOf(*plugin.config.messages.playerRankingInfo.toTypedArray())
        messages.replaceAll {
            it
                    .rep("%points%", rank.points.toString())
                    .rep("%kills%", rank.kills.toString())
                    .rep("%deaths%", rank.deaths.toString())
                    .rep("%assists%", rank.assists.toString())
                    .rep("%place%", plugin.manager.getPlace(rank.uniqueId).toString())
                    .rep("%name", Bukkit.getOfflinePlayer(rank.uniqueId).name)
        }
        sender.sendMessage(messages.toTypedArray())
    }

    @Subcommand("reload")
    @CommandPermission("command.ranking.reload")
    fun onCommandReload(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
        plugin.reloadConfig()
    }


}