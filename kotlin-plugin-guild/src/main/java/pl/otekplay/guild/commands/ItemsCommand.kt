package pl.otekplay.guild.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.otekplay.guild.GuildPlugin

@CommandAlias("gildia|g|guild")
@CommandPermission("command.guild")class ItemsCommand(plugin: GuildPlugin) : GuildCommand(plugin) {

    @Subcommand("itemy|przedmioty|items")
    @CommandPermission("command.guild.items")
    fun onCommand(player: Player) {
        val guild = manager.getGuildByMember(player.uniqueId)
        if (guild != null) return player.sendMessage(config.messages.youAlreadyGotGuild)
        manager.showMenuWithGuildItems(player)
    }

}