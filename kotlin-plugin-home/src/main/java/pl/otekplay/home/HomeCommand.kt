package pl.otekplay.home

import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildAPI
import pl.otekplay.plugin.api.PluginCommand
import pl.otekplay.plugin.rep
import pl.otekplay.teleporter.TeleportAPI
import java.util.*

class HomeCommand(private val plugin: HomePlugin) : PluginCommand() {


    @CommandAlias("dom|home")
    @CommandPermission("command.dom")
    @Description("Komenda zezwala na teleportacje do ustawionej lokalizacji.")
    @Syntax("[nazwa]")
    fun onCommandHome(player: Player, @Single @Optional name: String?) {
        val playerHome = plugin.manager.getHome(player.uniqueId) ?: return
        if (playerHome.homes.isEmpty()) return player.sendMessage(plugin.config.messages.youDontHaveHome)
        val homeName = name ?: plugin.config.homeDefaultName
        val home = playerHome.getHome(homeName)
                ?: return player.sendMessage(plugin.config.messages.homeWithThisNameNoExist.rep("%name%", homeName))
        val loc = home.location
        val guild = GuildAPI.getGuildByLocation(loc)
        if (guild != null && guild.getMember(player.uniqueId) == null) return player.sendMessage(plugin.config.messages.youCantTeleportToEnemyGuild)
        TeleportAPI.teleportPlayerToLocationWithDelay(player, loc, plugin.config.timeTeleportHome, true)
        player.sendMessage(plugin.config.messages.teleportingToHome)
    }

    @CommandAlias("listadomow|homelist")
    @CommandPermission("command.listadomow")
    @Description("Komenda pokazujelisty home.")
    fun onCommandHomeList(player: Player) {
        val playerHome = plugin.manager.getHome(player.uniqueId) ?: return
        player.sendMessage(plugin.config.messages.yourListHomes.rep("%homes%", Arrays.deepToString(playerHome.homes.map { it.name }.toTypedArray())))
    }


    @CommandAlias("ustawdom|sethome")
    @CommandPermission("command.ustawdom")
    @Description("Komenda zezwala na ustawienie lokalizacji do teleportacji.")
    @Syntax("[nazwa]")
    fun onCommandSetHome(player: Player, @Single @Optional name: String?) {
        val playerHome = plugin.manager.getHome(player.uniqueId) ?: return
        val location = player.location
        val guild = GuildAPI.getGuildByLocation(location)
        if (guild != null && guild.getMember(player.uniqueId) == null) return player.sendMessage(plugin.config.messages.youCantSetHomeAtEnemyGuild)
        val limitHomes = plugin.manager.getPlayerHomesLimit(player)
        if (playerHome.homes.size >= limitHomes) {
            if (name == null) return player.sendMessage(plugin.config.messages.youGotLimitHomes)
            if (playerHome.getHome(name) == null) return player.sendMessage(plugin.config.messages.youCantReplaceNoExistHome.rep("%name%", name))
            playerHome.setHome(name, location)
            player.sendMessage(plugin.config.messages.youSetHomeOnCords
                    .rep("%name%", name)
                    .rep("%x%", location.blockX.toString())
                    .rep("%y%", location.blockY.toString())
                    .rep("%z%", location.blockZ.toString()))
            return
        }
        val homeName = name ?: plugin.config.homeDefaultName
        playerHome.setHome(homeName, location)
        player.sendMessage(plugin.config.messages.youSetHomeOnCords
                .rep("%name%", homeName)
                .rep("%x%", location.blockX.toString())
                .rep("%y%", location.blockY.toString())
                .rep("%z%", location.blockZ.toString()))
    }

    @CommandAlias("homereload")
    @CommandPermission("command.homereload")
    fun onCommandReload(sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage(plugin.config.messages.youReloadedConfig)
    }
}