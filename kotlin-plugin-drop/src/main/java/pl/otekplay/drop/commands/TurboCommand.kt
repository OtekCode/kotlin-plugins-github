package pl.otekplay.drop.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Syntax
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import pl.otekplay.drop.DropPlugin
import pl.otekplay.drop.DropUser
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates

class TurboCommand(val plugin: DropPlugin) : PluginCommand() {


    @CommandAlias("turbodrop")
    @CommandPermission("command.turbodrop")
    @Syntax("<gracz> <czas>")
    fun onCommandDropTurbo(sender: CommandSender, player: OfflinePlayer, stringTime:String) {
        val user = plugin.manager.getUser(player.uniqueId) ?: return
        setTurboDrop(user,stringTime)
        sender.sendMessage(plugin.config.messages.youGivedTurboToPlayer.rep("%name%",player.name).rep("%date%",Dates.formatData(user.turboTime)))
    }
    @CommandAlias("turbodropall")
    @CommandPermission("command.turbodropall")
    @Syntax("<czas>")
    fun onCommandDropTurboAll(sender: CommandSender,stringTime:String) {
        Bukkit.broadcastMessage(plugin.config.messages.globalTurboDropHasBeenAdded.rep("%name%",sender.name))
        Bukkit.getOnlinePlayers().forEach {
            val user = plugin.manager.getUser(it.uniqueId) ?: return
            setTurboDrop(user,stringTime)
            it.sendMessage(plugin.config.messages.youGetTurboDrop.rep("%date%",Dates.formatData(user.turboTime)))
        }
    }


    private fun setTurboDrop(user: DropUser, stringTime: String){
        val parsedTime = Dates.parseString(stringTime)
        val time = if(user.hasTurbo) user.turboTime + parsedTime else System.currentTimeMillis() + parsedTime
        user.turboTime = time
        user.updateEntity()
    }
}