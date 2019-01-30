package pl.otekplay.mysterybox

import co.aikar.commands.annotation.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.rep
import java.util.*


@CommandAlias("mysterybox")
@CommandPermission("command.mysterybox")
class MysteryBoxCommand(private val plugin: MysteryBoxPlugin) : PluginCommand() {


    @Subcommand("give")
    @CommandPermission("command.mysterybox.give")
    @Syntax("<gracz> <nazwa> <ilosc>")
    fun onCommandGive(sender: CommandSender, @Flags("other") player: Player, name: String, amount: Int) {
        val box = plugin.manager.boxes.singleOrNull { it.name.equals(name, true) }
                ?: return sender.sendMessage(plugin.config.messages.boxWithSpecifyNameNoExist.rep("%name%", name))
        val item = box.item.toItemStack()
        if (amount in 1..item.maxStackSize) item.amount = amount
        Items.addItem(player, item)
        sender.sendMessage(plugin.config.messages.youGivedBoxToPlayer.rep("%name%", player.name).rep("%name%", name).rep("%amount%", amount.toString()))
    }

    @Subcommand("list")
    @CommandPermission("command.mysterybox.list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.boxList.rep("%boxes%", Arrays.deepToString(plugin.manager.boxes.map { it.name }.toTypedArray())))
    }

    @Subcommand("reload")
    @CommandPermission("command.mysterybox.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.manager.loadBoxes()
        sender.sendMessage(plugin.config.messages.boxesHasBeenReloaded)
    }
}