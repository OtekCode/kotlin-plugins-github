package pl.otekplay.guild.commands

import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Players
import pl.otekplay.plugin.rep
import java.util.*

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")
class InfoCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Syntax("[tag]")
    @Subcommand("info")
    @CommandPermission("command.guild.info")

    fun onCommand(sender: CommandSender, @Optional tag: String?) {
        val guild = if (tag != null) manager.getGuildByTag(tag) else manager.getGuildByMember((sender as Player).uniqueId)

        if (guild == null) {
            sender.sendMessage(
                    if (tag != null)
                        config.messages.cantFindGuildWithTag.rep("%tag%", tag)
                    else
                        config.messages.youDontHaveGuild)
            return
        }
        val message = arrayListOf(*plugin.config.messages.guildInfoMessage.toTypedArray())
        message.replaceAll {
            it
                    .rep("%tag%", guild.tag)
                    .rep("%name%", guild.name)
                    .rep("%leader%", Bukkit.getOfflinePlayer(guild.leaderId).name)
                    .rep("%members%", Players.getPlayersStringColored(guild.members.map { it.offlinePlayer }))
                    .rep("%allies%", Arrays.toString(guild.allyGuilds.map { manager.getGuildById(it) !!.tag }.toTypedArray()))
                    .rep("%cuboid%", guild.cuboidSize.toString())
                    .rep("%create%", Dates.formatData(guild.createDate))
                    .rep("%expire%", Dates.formatData(guild.expireTime))
                    .rep("%lives%", guild.lives.toString())
                    .rep("%points%",manager.getGuildPoints(guild).toString())
                    .rep("%kills%",manager.getGuildKills(guild).toString())
                    .rep("%deaths%",manager.getGuildDeaths(guild).toString())
                    .rep("%assists%",manager.getGuildAssists(guild).toString())
                    .rep("%place%",manager.getGuildPlace(guild).toString())
                    .rep("%protection%", Dates.formatData(manager.getGuildProtectionTime(guild)))
        }
        sender.sendMessage(message.toTypedArray())
    }
}