package pl.otekplay.enchants

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand


class EnchantCommand(val plugin: EnchantPlugin) : PluginCommand() {


    @CommandAlias("enchantsreload")
    @CommandPermission("command.enchant.reload")
    fun onEnchantReload(player: Player) {
        player.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}