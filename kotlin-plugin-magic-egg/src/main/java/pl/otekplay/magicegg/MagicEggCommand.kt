package pl.otekplay.magicegg

import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.rep
import java.util.*


@CommandAlias("magicegg")
@CommandPermission("command.magic.egg")
class MagicEggCommand(private val plugin: MagicEggPlugin) : PluginCommand() {

    @Subcommand("give")
    @CommandPermission("command.magic.egg.give")
    @Syntax("<gracz> <nazwa> [ilosc]")
    fun onCommandGive(sender: CommandSender, @Flags("other") player: Player, name: String, @Optional amount: Int?) {
        val egg = plugin.manager.getMagicEgg(name)
                ?: return sender.sendMessage(plugin.config.messages.eggWithSpecifyNameNoExist.rep("%name%", name))
        val item = egg.result.toItemStack()
        item.amount = amount ?: 1
        sender.sendMessage(plugin.config.messages.eggHasBeenGived.rep("%name%", player.name).rep("%name%", name).rep("%amount%", item.amount.toString()))
        Items.addItem(player,item)
    }

    @Subcommand("list")
    @CommandPermission("command.magic.egg.list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.eggList.rep("%eggs%", Arrays.deepToString(plugin.manager.eggs.map { it.name }.toTypedArray())))
    }

    @Subcommand("reload")
    @CommandPermission("command.magic.egg.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.configHasBeenReloaded)
    }
}