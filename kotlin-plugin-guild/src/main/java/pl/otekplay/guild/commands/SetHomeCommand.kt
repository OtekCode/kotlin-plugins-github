package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class SetHomeCommand(plugin: GuildPlugin) : GuildCommand(plugin) {


    @Subcommand("ustawdom|sethome")
    @CommandPermission("command.guild.sethome")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId) ?: return player.sendMessage(config.messages.youDontHaveGuild)
        if(!guild.isLeader(player.uniqueId)) return player.sendMessage(config.messages.youAreNotLeader)
        val location = player.location
        val locGuild = manager.getGuildByCords(location.blockX,location.blockZ)
        if(locGuild != guild) return player.sendMessage(config.messages.guildCantSetHomeOnOtherGuild)
        manager.setHomeGuild(guild,location)
        manager.broadcastGuild(guild,config.messages.guildHomeHasBeenChanged)
    }
}