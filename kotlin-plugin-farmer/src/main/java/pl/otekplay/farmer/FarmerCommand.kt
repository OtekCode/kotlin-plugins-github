package pl.otekplay.farmer

import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.rep
import java.util.*

@CommandAlias("farmer")
@CommandPermission("command.farmer")
class FarmerCommand(private val plugin: FarmerPlugin) : PluginCommand() {


    @Subcommand("give")
    @CommandPermission("command.farmer.give")
    @Syntax("<gracz> <farmer> [ilosc]")
    fun onCommandGive(sender: CommandSender, @Flags("other") player: Player, name: String, @Optional amount: Int?) {
        val farmer = plugin.manager.farmers.singleOrNull { it.name.contains(name, true) }
                ?: return sender.sendMessage(plugin.config.messages.farmerNoExist.rep("%name%", name))
        val item = farmer.recipe.result.toItemStack()
        item.amount = amount ?: 1
        Items.addItem(player, item)
        sender.sendMessage(plugin.config.messages.youGivedFarmer.rep("%name%", player.name).rep("%name%", name).rep("%amount%", item.amount.toString()))
    }

    @Subcommand("list")
    @CommandPermission("command.farmer.list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.listFarmers.replace("%farmers%", Arrays.deepToString(plugin.manager.farmers.map { it.name }.toTypedArray())))
    }

    @Subcommand("reload")
    @CommandPermission("command.farmer.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}