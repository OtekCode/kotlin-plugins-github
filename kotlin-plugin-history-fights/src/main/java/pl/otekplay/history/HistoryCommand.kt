package pl.otekplay.history

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Flags
import co.aikar.commands.annotation.Syntax
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand

class HistoryCommand(val plugin: HistoryPlugin): PluginCommand() {

    @CommandAlias("historia|history")
    @CommandPermission("command.historia")
    @Syntax("<gracz>")
    fun onCommand(sender: Player,@Flags("other") offlinePlayer: OfflinePlayer){
        plugin.menu.openHistoryMenu(sender,offlinePlayer)
    }
}