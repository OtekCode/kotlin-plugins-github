package pl.otekplay.tools.system.vanish

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.tag.TagAPI

class VanishCommand(val manager: VanishManager) : PluginCommand() {

    @CommandAlias("vanish|v|poof")
    @Syntax("[player]")
    @CommandPermission("command.vanish")
    fun onCommand(player: Player) {
        manager.changeStateVanish(player.uniqueId)
        TagAPI.refresh(player)
        if (manager.hasVanish(player.uniqueId)) {
            manager.hidePlayer(player)
            player.sendMessage(manager.config.messages.yourVanishHasBeenEnabled)
            return
        }
        manager.showPlayer(player)
        player.sendMessage(manager.config.messages.yourVanishHasBeenDisabled)
    }
}