package pl.otekplay.worldborder

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep

@CommandAlias("worldborder|wb")
@CommandPermission("command.worldborder")
class WorldBorderCommand(private val plugin: WorldBorderPlugin) : PluginCommand() {

    @Subcommand("size")
    @Syntax("<wielkosc>")
    @CommandPermission("command.worldborder.size")
    fun onCommandSize(sender: CommandSender, size: Int) {
        val world = Bukkit.getWorlds()[0]
        world.worldBorder.size = size.toDouble()
        sender.sendMessage(plugin.config.messages.youSetSizeBorder.rep("%size%", size.toString()))
    }

    @Subcommand("center")
    @Syntax("<x> <z>")
    @CommandPermission("command.worldborder.center")
    fun onCommandCenter(sender: CommandSender, x: Int, z: Int) {
        val world = Bukkit.getWorlds()[0]
        world.worldBorder.setCenter(x.toDouble(), z.toDouble())
        sender.sendMessage(plugin.config.messages.youSetCenterBorder.rep("%x%", x.toString()).rep("%z%", z.toString()))
    }

    @Subcommand("info")
    @CommandPermission("command.worldborder.info")
    fun onCommandInfo(sender: CommandSender) {
        val world = Bukkit.getWorlds()[0]
        val border = world.worldBorder
        sender.sendMessage(plugin.config.messages.borderInfo
                .rep("%size%", border.size.toString())
                .rep("%x%", border.center.blockX.toString())
                .rep("%z%", border.center.blockZ.toString())
        )
    }

    @Subcommand("reload")
    @CommandPermission("command.worldborder.reload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}