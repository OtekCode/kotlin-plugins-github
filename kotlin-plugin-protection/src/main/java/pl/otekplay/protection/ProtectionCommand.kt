package pl.otekplay.protection

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.util.Dates

@CommandAlias("ochrona|protection")
@CommandPermission("command.protection")
class ProtectionCommand(private val plugin: ProtectionPlugin) : PluginCommand() {
    private val config = plugin.config
    private val manager = plugin.manager


    @Subcommand("wylacz|off")
    @CommandPermission("command.protection.off")
    fun onCommandDisable(player: Player) {
        val uniqueId = player.uniqueId
        if (! manager.isProtected(uniqueId)) return player.sendMessage(config.messages.yourProtectionIsDisabled)
        manager.disableProtection(uniqueId)
        player.sendMessage(config.messages.yourProtectionHasBeenDisabled)
    }

    @Subcommand("info")
    @CommandPermission("command.protection.info")
    fun onCommandInfo(player: Player) {
        return if (manager.isProtected(player.uniqueId))
            player.sendMessage(config.messages.infoProtectionTime.replace("%time%", Dates.formatData(manager.getProtectionTimeEnd(player.uniqueId))))
        else
            player.sendMessage(config.messages.yourProtectionIsDisabled)
    }

    @Subcommand("reload")
    @CommandPermission("command.protection.reload")
    fun onCommandReload(sender: CommandSender) {
       plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }




}