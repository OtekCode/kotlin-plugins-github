package pl.otekplay.guild.commands

import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.rep

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class RemoveCommand(plugin: GuildPlugin) : GuildCommand(plugin) {
    @Subcommand("wyrzuc|remove")
    @CommandPermission("command.guild.remove")
    @Syntax("<gracz>")
    fun onCommand(player: Player, @Flags("other") other: OfflinePlayer) {
        val guild = manager.getGuildByMember(player.uniqueId)
        if (guild == null) {
            player.sendMessage(config.messages.youDontHaveGuild)
            return
        }
        val remover = guild.getMember(player.uniqueId)!!
        if (!guild.isLeader(remover.uniqueId)) {
            player.sendMessage(config.messages.youAreNotLeader)
            return
        }
        val remove = guild.getMember(other.uniqueId)
        if(remove == null){
            player.sendMessage(config.messages.playerIsNotInYourGuild.rep("%name%", other.name))
            return
        }

        if (guild.isLeader(remove.uniqueId)) {
            player.sendMessage(config.messages.cantKickPlayerIsLeader.rep("%name%", other.name))
            return
        }
        Bukkit.broadcastMessage(config.messages.playerGotKickFromGuild.rep("%name%",other.name).rep("%tag%",guild.tag))
        if(!other.isOnline){
            return
        }
        other.player.sendMessage(config.messages.youGotKickedFromGuild.rep("%remover%",player.name))
        plugin.manager.removeGuildMember(remove.uniqueId)
    }
}