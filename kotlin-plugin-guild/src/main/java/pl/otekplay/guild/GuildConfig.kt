package pl.otekplay.guild

import org.bukkit.ChatColor
import org.bukkit.Material
import pl.otekplay.guild.config.GuildConfigItem
import pl.otekplay.menu.api.MenuConfig
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions
import pl.otekplay.plugin.util.Dates
import java.util.concurrent.TimeUnit

class GuildConfig : PluginConfiguration {
    val messages = GuildMessages()
    val guildDistanceFromBorderSpawn = 200
    val guildCuboidYLocation = 40
    val guildStartLives = 3
    val guildMaxLives = 5
    val guildCuboidStartSize = 20
    val guildCuboidDistanceBetweenGuilds = 150
    val guildMembersStartSize = 3
    val guildTaskTimerExpire = 20L * 60
    val guildStartProtectionTime = TimeUnit.DAYS.toMillis(1)
    val guildAfterDestroyProtectionTime = TimeUnit.DAYS.toMillis(1)
    val guildRenewStartTime = TimeUnit.DAYS.toMillis(1)
    val guildJoinItemId = Material.EMERALD.id
    val guildJoinItemData: Short = 0
    val guildJoinPerMemberAmount = 5
    val guildItemsMenuOptions = ConfigMenuOptions("&7Itemy na Gildie", 5, ConfigMenuFill(true, MenuConfig.fillerItem))
    val guildCreateItems = listOf(
            GuildConfigItem("Melon", Material.MELON.id, 48, 0, listOf(""), Dates.formatData(System.currentTimeMillis())),
            GuildConfigItem("Ziemniaki", Material.BAKED_POTATO.id, 48, 0, listOf(), Dates.formatData(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30))),
            GuildConfigItem("Diamenty", Material.DIAMOND_BLOCK.id, 48, 0, listOf(), Dates.formatData(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60))),
            GuildConfigItem("Refile", Material.GOLDEN_APPLE.id, 32, 0, listOf(), Dates.formatData(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(90))),
            GuildConfigItem("Ksiazki", Material.BOOKSHELF.id, 24, 0, listOf(), Dates.formatData(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(120))),
            GuildConfigItem("Emeraldy", Material.EMERALD.id, 64, 0, listOf(), Dates.formatData(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(150))),
            GuildConfigItem("Barwnik", Material.INK_SACK.id, 48, 1, listOf(), Dates.formatData(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(180)))
    )
    val guildCreateAvailableTime = Dates.formatData(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2))
    val guildCreateItemUnknown = ConfigItem("Ukryty!", Material.STAINED_GLASS_PANE.id, 1, 5.toShort(), listOf("Pokaze sie dopiero: %date%"))
    val guildMenuHasItemName = "${ChatColor.RED}Posiadasz!"
    val guildMenuHasItemId = Material.STAINED_GLASS_PANE.id
    val guildMenuHasItemData = 5.toShort()
    val guildMenuNeedItemName = "${ChatColor.RED}Nie posiadasz!"
    val guildMenuNeedItemId = Material.STAINED_GLASS_PANE.id
    val guildMenuNeedItemData = 14.toShort()
    val guildMenuItemLoreNeed = arrayListOf(
            "&7Posiadasz: &6%hasAmount%",
            "&7Potrzebujesz: &6%totalAmount%",
            "&7Brakuje: &6%needAmount%"
    )
    val guildMenuItemLoreHas = arrayListOf("&7Posiadasz wystarczajaca ilosc!")
    val guildTagFriendly = "[&2%tag%&f]"
    val guildTagAlly = "[&e%tag%&f]"
    val guildTagEnemy = "[&4%tag%&f]"
    val guildChatFormatReplace = "%guild%"
    val guildRemindAboutEnemiesTime = 30000L
    val guildBlockBuildTime = 60000L
    val options = ConfigMenuOptions("Top Gildie", 6, ConfigMenuFill(false, ConfigItem("elo", Material.DIAMOND.id)))
    val topGuildSize = 9
    val topGuildName = "&7Gildia &6%tag%"
    val topGuildLore = listOf(
            "&7Tag: &6%tag%",
            "&7Leader: &6%leader%",
            "&7Miejsce: &6%place%",
            "&7Punkty: &6%points%",
            "&7Zabojstwa:&6 %kills%",
            "&7Zgony: &6%deaths%",
            "&7Assysty: &6%assists%"
    )

}