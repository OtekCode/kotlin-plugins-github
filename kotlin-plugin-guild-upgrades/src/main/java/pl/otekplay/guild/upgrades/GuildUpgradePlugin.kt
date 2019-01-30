package pl.otekplay.guild.upgrades

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import pl.otekplay.guild.GuildAPI
import pl.otekplay.guild.holders.Guild
import pl.otekplay.guild.upgrades.api.RenewMenuConfig
import pl.otekplay.guild.upgrades.api.UpgradeConfig
import pl.otekplay.guild.upgrades.api.UpgradeMenuConfig
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Items
import java.util.logging.Logger

@PluginAnnotation(
        name = "GuildUpgradePlugin",
        dependency = ["GuildPlugin"]
)
class GuildUpgradePlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

     lateinit var config: GuildUpgradeConfig
    private lateinit var renewMenuConfig: RenewMenuConfig
    private lateinit var cuboidMenuConfig: UpgradeMenuConfig
    private lateinit var membersMenuConfig: UpgradeMenuConfig
    private val cuboidUpgrade = object : GuildUpgrade {
        override fun upgrade(guild: Guild, value: Int) {
            guild.cuboidSize = value
            guild.updateEntity()
            GuildAPI.broadcastGuild(guild, config.messages.guildCuboidHasBeenUpgradedTo.rep("%value%", value.toString()))
        }
    }
    private val membersUpgrade = object : GuildUpgrade {
        override fun upgrade(guild: Guild, value: Int) {
            guild.membersSize = value
            guild.updateEntity()
            GuildAPI.broadcastGuild(guild, config.messages.guildMembersHasBeenUpgradedTo.rep("%value%", value.toString()))
        }
    }


    override fun onEnable() {
        config = loadConfig(GuildUpgradeConfig::class)
        renewMenuConfig = loadConfig(RenewMenuConfig::class, "RenewConfig")
        cuboidMenuConfig = loadConfig(UpgradeMenuConfig::class, "CuboidConfig")
        membersMenuConfig = loadConfig(UpgradeMenuConfig::class, "MembersConfig")
        registerCommand(GuildUpgradeCommand(this))
    }

    fun reloadConfig(){
        config = loadConfig(GuildUpgradeConfig::class)
        renewMenuConfig = loadConfig(RenewMenuConfig::class, "RenewConfig")
        cuboidMenuConfig = loadConfig(UpgradeMenuConfig::class, "CuboidConfig")
        membersMenuConfig = loadConfig(UpgradeMenuConfig::class, "MembersConfig")
    }

    private fun isNextUpgrade(guildValue: Int, value: Int, upgradeConfigs: Collection<UpgradeConfig>) = upgradeConfigs.filter { it.value > guildValue }.filter { it.value != value }.none { value > it.value }

    fun buildUpgradeMenu(player: Player, guild: Guild) {
        val menu = Menu(config.upgradeMenuOptions)
        buildOptionUpgrade(menu, cuboidMenuConfig, guild, guild.cuboidSize, cuboidUpgrade)
        buildOptionUpgrade(menu, membersMenuConfig, guild, guild.membersSize, membersUpgrade)
        buildOptionRenew(menu, guild)
        menu.open(player)
    }

    private fun buildOptionRenew(menu: Menu, guild: Guild) {
        val cfg = renewMenuConfig
        if (! cfg.enabled) return
        val static = Item(cfg.optionItem.item.toItemStack(), object : ItemClick {
            override fun click(player: Player) {
                openRenewMenu(player, guild)
            }
        })
        menu.setItem(cfg.optionItem.slot, static)
    }

    private fun openRenewMenu(player: Player, guild: Guild) {
        val cfg = renewMenuConfig
        if (! cfg.enabled) return
        val menu = Menu(cfg.menuOptions)
        cfg.renews.forEach {
            val slot = it.buyMenuItem.menuItem.slot
            val icon = it.buyMenuItem.menuItem.item.toItemStack()
            menu.setItem(slot, Item(icon, object : ItemClick {
                override fun click(player: Player) {
                    if (! checkItems(player, it.buyMenuItem.neededItems)) return
                    guild.expireTime = guild.expireTime + it.time
                    guild.updateEntity()
                    GuildAPI.broadcastGuild(guild, config.messages.guildHasBeenRenew.rep("%time%", Dates.formatData(guild.expireTime)))
                    openRenewMenu(player, guild)
                }
            }))
        }
        menu.open(player)
    }

    private fun buildOptionUpgrade(menu: Menu, cfg: UpgradeMenuConfig, guild: Guild, guildValue: Int, upgrade: GuildUpgrade) {
        if (! cfg.enabled) return
        val static = Item(cfg.optionItem.item.toItemStack(), object : ItemClick {
            override fun click(player: Player) {
                openUpgradeMenu(player, guild, cfg, guildValue, upgrade)
            }
        })
        menu.setItem(cfg.optionItem.slot, static)
    }

    private fun openUpgradeMenu(player: Player, guild: Guild, cfg: UpgradeMenuConfig, guildValue: Int, upgrade: GuildUpgrade) {
        val menu = Menu(config.upgradeMenuOptions)
        val upgrades = cfg.upgrades
        upgrades.sortBy { it.value }
        upgrades.forEach {
            val has = guildValue >= it.value
            val slot = it.buyMenuItem.menuItem.slot
            val hasSlot = slot + cfg.upgradeHasSlotItem
            val icon = it.buyMenuItem.menuItem.item.toItemStack()
            menu.setItem(slot, Item(icon, if (has) null else object : ItemClick {
                override fun click(player: Player) {
                    if (! isNextUpgrade(guildValue, it.value, upgrades)) {
                        player.sendMessage(config.messages.youNeedLessUpgrade)
                        return
                    }
                    if (! checkItems(player, it.buyMenuItem.neededItems)) return
                    upgrade.upgrade(guild, it.value)
                    openUpgradeMenu(player, guild, cfg, it.value, upgrade)
                }
            }))
            menu.setItem(hasSlot, Item(if (has) hasItem else needItem))
        }
        menu.open(player)
    }

    private val hasItem: ItemStack get() = config.hasItem.toItemStack()
    private val needItem: ItemStack get() = config.needItem.toItemStack()

    private fun checkItems(player: Player, items: Collection<ConfigItem>): Boolean {
        if (! hasItems(player.inventory, items)) {
            showNeededItems(player, items)
            return false
        }
        removeItems(player.inventory, items)
        return true
    }

    private fun showNeededItems(player: Player, items: Collection<ConfigItem>) {
        val menu = Menu(config.itemsMenuOptions)
        items.forEachIndexed { i, item -> menu.setItem(i, Item(item.toItemStack())) }
        menu.open(player)
    }

    private fun hasItems(inventory: Inventory, items: Collection<ConfigItem>) = Items.hasItems(inventory, items.map { it.toItemStack() })

    private fun removeItems(inventory: Inventory, items: Collection<ConfigItem>) = items.map { it.toItemStack() }.forEach { Items.consumeItem(inventory, it.type, it.amount, it.durability) }
}