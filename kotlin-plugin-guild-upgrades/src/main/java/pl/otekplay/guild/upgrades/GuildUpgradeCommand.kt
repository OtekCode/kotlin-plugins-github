package pl.otekplay.guild.upgrades

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildAPI
import pl.otekplay.plugin.api.PluginCommand

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class GuildUpgradeCommand(
        private val plugin: GuildUpgradePlugin
) : PluginCommand() {

    @Subcommand("upgrade")
    @CommandPermission("command.guild.upgrade")
    fun onCommand(player: Player) {
        val config = GuildAPI.getGuildConfig()
        val guild = GuildAPI.getGuildByMember(player.uniqueId)
                ?: return player.sendMessage(config.messages.youDontHaveGuild)
        if (! guild.isLeader(player.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        plugin.buildUpgradeMenu(player, guild)
    }

    @Subcommand("upgradereload")
    @CommandPermission("command.guild.upgrade.reload")
    fun onCommandReload(sender: Player) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}