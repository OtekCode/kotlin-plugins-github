package pl.otekplay.generator

import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Items
import java.util.*


@CommandAlias("generator")
@CommandPermission("command.generator")
class GeneratorCommand(private val plugin: GeneratorPlugin): PluginCommand() {


    @Subcommand("list")
    @CommandPermission("command.generator.list")
    fun onCommandList(sender: CommandSender) {
        sender.sendMessage(plugin.config.messages.listGenerators.rep("%generators%", Arrays.deepToString(plugin.manager.configGenerators.map { it.name }.toTypedArray())))
    }

    @Subcommand("give")
    @CommandPermission("command.generator.give")
    @Syntax("<gracz> <generator> [ilosc]")
    fun onCommandGive(sender: CommandSender, @Flags("other") player: Player, generatorName: String, @Optional amount: Int?) {
        val generator = plugin.manager.configGenerators.singleOrNull { it.name.contains(name, true) }
                ?: return sender.sendMessage(plugin.config.messages.generatorWithSpecifyNameNoExist.rep("%name%", name))
        val item = generator.recipe.result.toItemStack()
        item.amount = amount ?: 1
        Items.addItem(player, item)
        sender.sendMessage(plugin.config.messages.youGivedGeneratorToPlayer.rep("%name%", player.name).rep("%generator%", generatorName).rep("%amount%", item.amount.toString()))
    }

    @Subcommand("reload")
    @CommandPermission("command.generator.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.manager.loadGenerators()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}