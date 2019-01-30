package pl.otekplay.tab

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.entity.Player


@CommandAlias("tab")
class TabCommand(val plugin: TabPlugin) : pl.otekplay.plugin.api.PluginCommand() {

    @Subcommand("reload")
    fun onCommand(sender:CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messageYouReloadedConfig)
    }
}