package pl.otekplay.teleporter.commands

import co.aikar.commands.annotation.*
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import pl.otekplay.teleporter.TeleportPlugin

@CommandAlias("tp|teleport")
@CommandPermission("command.teleport")
class TeleportCommand(private val plugin: TeleportPlugin) : PluginCommand() {

    @Subcommand("go")
    @Description("Komenda pozwala na teleportacje do gracza")
    @CommandPermission("command.teleport.go")
    @CommandCompletion("@players")
    @Syntax("<gracz>")
    private fun onCommandTo(sender: Player, @Flags("other") tp: Player) {
        sender.teleport(tp)
        sender.sendMessage(plugin.config.messages.teleportToPlayer.rep("%name%", tp.name))
    }

    @CommandAlias("teleporthere|tphere")
    @CommandPermission("command.teleport.here")
    @CommandCompletion("@players")
    @Subcommand("here")
    @Syntax("<gracz>")
    fun onCommandHere(sender: Player, @Flags("other") tp: Player) {
        tp.teleport(sender)
        sender.sendMessage(plugin.config.messages.teleportHerePlayer.rep("%name%", tp.name))
        tp.sendMessage(plugin.config.messages.youGotTeleportByPlayer.rep("%name%", sender.name))
    }

    @CommandAlias("tpcords|cords")
    @Subcommand("cords")
    @CommandPermission("command.teleport.cords")
    @Syntax("<x> <z> [y]")
    @Description("Komenda sluzy do teleportacji na dane kordynaty.")
    fun onCommandCords(sender: Player, x: Int, z: Int, @Optional() _y: Int?) {
        val y = _y ?: sender.world.getHighestBlockYAt(x, z)
        val loc = Location(sender.world, x.toDouble(), y.toDouble(), z.toDouble())
        sender.teleport(loc)
        sender.sendMessage(plugin.config.messages.teleportToCords.rep("%x%", x.toString()).rep("%y%", y.toString()).rep("%z%", z.toString()))
    }


    @UnknownHandler
    @CommandPermission("command.teleport.players")
    @Description("Komenda pozwala na teleportacje gracza do gracza")
    @CommandCompletion("@players @players")
    @Syntax("<gracz> [gracz]")
    fun onCommand(sender: CommandSender, @Flags("other") from: Player, @Optional @Flags("other") to: Player?) {
        if (to != null) return executeAsConsole(sender, from, to)
        if (sender is Player) return onCommandTo(sender, from)
        sender.sendMessage(plugin.config.messages.cantTeleportToPlayerFromConsole)
    }


    private fun executeAsConsole(sender: CommandSender, from: Player, to: Player) {
        from.teleport(to)
        sender.sendMessage(plugin.config.messages.teleportPlayerToPlayer.rep("%to%", to.name).rep("%from%", from.name))
        from.sendMessage(plugin.config.messages.teleportPlayerToPlayerBy.rep("%to%", to.name).rep("%sender%", sender.name))
    }


}