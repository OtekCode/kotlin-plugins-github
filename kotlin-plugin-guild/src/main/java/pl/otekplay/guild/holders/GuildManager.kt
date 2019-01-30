package pl.otekplay.guild.holders

import com.google.common.cache.CacheBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.SkullType
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.guild.GuildPlugin
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.plugin.builders.ItemBuilder
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.util.Locations
import pl.otekplay.ranking.RankingAPI
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class GuildManager(val plugin: GuildPlugin) {
    private val _guilds = ConcurrentHashMap<UUID, Guild>()
    val guilds: Collection<Guild> get() = Collections.unmodifiableCollection(_guilds.values)


    init {
        DatabaseAPI.loadAll<Guild>("guilds", {
            it.forEach { _guilds[it.guildId] = it }
            plugin.logger.info("Loaded ${_guilds.size} guilds")
        })

    }

    fun getGuildById(uniqueId: UUID) = _guilds[uniqueId]

    fun getGuildByTag(tag: String) = guilds.singleOrNull { it.tag.equals(tag, true) }

    fun getGuildByName(name: String) = guilds.singleOrNull { it.name.equals(name, true) }

    fun getGuildByMember(uniqueId: UUID) = guilds.singleOrNull { it.getMember(uniqueId) != null }

    fun getGuildByLocation(location: Location) = getGuildByCords(location.blockX, location.blockZ)

    fun getGuildByCords(x: Int, z: Int) = guilds.singleOrNull { Math.abs(it.center.blockX - x) <= it.cuboidSize && Math.abs(it.center.blockZ - z) <= it.cuboidSize }

    fun getGuildsNear(x: Int, z: Int, size: Int) = guilds.filter { Math.abs(it.center.blockX - x) <= size && Math.abs(it.center.blockZ - z) <= size }

    fun removeGuildMember(uniqueId: UUID) {
        plugin.logger.info("Removing member $uniqueId from guild...")
        val guild = getGuildByMember(uniqueId) ?: return
        val member = guild.getMember(uniqueId) ?: return
        guild.members.remove(member)
        guild.updateEntity()
        plugin.logger.info("Member $uniqueId has been removed from guild ${guild.guildId}")
    }

    fun createGuildMember(guild: Guild, uniqueId: UUID) {
        plugin.logger.info("Member $uniqueId trying join to guild ${guild.guildId}")
        val member = GuildMember(uniqueId, System.currentTimeMillis())
        guild.members.add(member)
        guild.updateEntity()
        plugin.logger.info("Member $uniqueId joined to guild ${guild.guildId}")
    }


    fun createGuild(tag: String, name: String, leaderId: UUID, location: Location, lives: Int, sizeCuboid: Int, sizeMembers: Int) {
        plugin.logger.info("Creating guild tag: $tag name: $name LeaderId: $leaderId")
        val leaderMember = GuildMember(leaderId, System.currentTimeMillis())
        val id = UUID.randomUUID()
        val guild = Guild(id,
                tag,
                name,
                location.clone().apply { y = plugin.config.guildCuboidYLocation.toDouble() },
                location.clone(),
                leaderId,
                System.currentTimeMillis(),
                lives,
                System.currentTimeMillis(),
                sizeCuboid,
                sizeMembers,
                System.currentTimeMillis() + plugin.config.guildRenewStartTime,
                hashSetOf(leaderMember),
                hashSetOf(),
                hashSetOf(),
                hashSetOf()
        )
        _guilds[id] = guild
        if (! _guilds.containsKey(id)) return
        guild.insertEntity()
        createGuildRoom(guild)
        plugin.logger.info("Guild $tag has been created with ID: $id")
    }

    private fun createGuildRoom(guild: Guild) {
        plugin.logger.info("Creating guild ${guild.tag} room")
        plugin.runSync(Runnable {
            val location = guild.center
            var center = guild.center.clone()
            for (loc in Locations.getSquare(center, 4, 3)) {
                loc.block.type = Material.AIR
            }
            for (loc in Locations.getSquare(center, 4)) {
                loc.block.type = Material.OBSIDIAN
            }
            for (loc in Locations.getCorners(center, 4, 3)) {
                loc.block.type = Material.OBSIDIAN
            }
            center.add(0.0, 4.0, 0.0)
            for (loc in Locations.getWalls(center, 4)) {
                loc.block.type = Material.OBSIDIAN
            }
            center = Location(Bukkit.getWorlds()[0], location.x, location.y, location.z)
            center.block.type = Material.BEDROCK
            center.clone().add(0.0, 1.0, 0.0).block.type = Material.DRAGON_EGG
            plugin.logger.info("Guild ${guild.tag} room has been created.")
        })
    }

    private fun removeGuildRoom(guild: Guild) {
        plugin.logger.info("Guild ${guild.tag} room removing...")
        val run = Runnable {
            val center = guild.center.clone()
            center.subtract(0.0, 3.0, 0.0).block.type = Material.AIR
            center.add(0.0, 2.0, 0.0).block.type = Material.AIR
            plugin.logger.info("Guild ${guild.tag} room removed.")
        }
        plugin.runSync(run)
    }

    private fun removeGuildAllies(guild: Guild) {
        plugin.logger.info("Removing all allies guild ${guild.tag}")
        guilds.filter { it.allyGuilds.contains(guild.guildId) }.filter { it.allyGuilds.remove(guild.guildId) }.forEach { it.updateEntity() }
        guilds.filter { it.allyInvites.contains(guild.guildId) }.filter { it.allyInvites.remove(guild.guildId) }.forEach { it.updateEntity() }
        guild.allyInvites.clear()
        guild.allyGuilds.clear()
        plugin.logger.info("Removed all allies with guild ${guild.tag}")
    }

    fun removeGuild(guild: Guild) {
        plugin.logger.info("Removing guild ${guild.tag}")
        removeGuildAllies(guild)
        removeGuildRoom(guild)
        guild.deleteEntity()
        _guilds.remove(guild.guildId)
        plugin.logger.info("Guild with Id ${guild.tag} has been removed.")
    }

    fun changeLeader(guild: Guild, newLeader: UUID) {
        plugin.logger.info("Changing leader guild ${guild.tag} to player $newLeader")
        val oldLeader = guild.leaderId
        guild.leaderId = newLeader
        guild.updateEntity()
        plugin.logger.info("Guild ${guild.tag} changed leader old: $oldLeader new: $newLeader")
    }

    fun createInvite(guild: Guild, invitedId: UUID) {
        plugin.logger.info("Guild ${guild.tag} inviting player $invitedId")
        guild.invites.add(invitedId)
        guild.updateEntity()
        plugin.logger.info("Guild ${guild.tag} invited player $invitedId")
    }

    fun removeInvite(guild: Guild, invitedId: UUID) {
        plugin.logger.info("Guild ${guild.tag} removing invite player $invitedId")
        guild.invites.remove(invitedId)
        guild.updateEntity()
        plugin.logger.info("Guild ${guild.tag} removed invite player $invitedId")
    }


    fun setHomeGuild(guild: Guild, location: Location) {
        plugin.logger.info("Guild ${guild.tag} setting home...")
        guild.home = location
        guild.updateEntity()
        plugin.logger.info("Guild ${guild.tag} home has been changed.")
    }

    fun hasAllyInvite(g1: Guild, g2: Guild) = g1.allyInvites.contains(g2.guildId)
    fun hasAlly(g1: Guild, g2: Guild) = g1.allyGuilds.contains(g2.guildId) && g2.allyGuilds.contains(g1.guildId)
    fun createAllyInvite(g1: Guild, g2: Guild) {
        plugin.logger.info("Guild ${g1.tag} trying invite guild  ${g2.tag} to ally")
        g1.allyInvites.add(g2.guildId)
        g1.updateEntity()
        plugin.logger.info("Guild ${g1.tag} invited guild ${g2.tag} to ally")
    }

    fun removeAllyInvite(g1: Guild, g2: Guild) {
        plugin.logger.info("Guild ${g1.tag} trying remove invite ally guild  ${g2.tag}")
        g1.allyInvites.remove(g2.guildId)
        g1.updateEntity()
        plugin.logger.info("Guild ${g1.tag} invite to ally guild ${g2.tag} has been removed.")
    }


    fun createGuildAlly(g1: Guild, g2: Guild) {
        plugin.logger.info("Guild ${g1.tag} trying to ally with  ${g2.tag}")
        g1.allyGuilds.add(g2.guildId)
        g2.allyGuilds.add(g1.guildId)
        g1.updateEntity()
        g2.updateEntity()
        plugin.logger.info("Guild ${g1.tag} is ally with ${g2.tag}")
    }

    fun removeGuildAlly(g1: Guild, g2: Guild) {
        plugin.logger.info("Guild ${g1.tag} trying to peel allywith  ${g2.tag}")
        if (! g1.allyGuilds.contains(g2.guildId) || ! g2.allyGuilds.contains(g1.guildId)) {
            return
        }
        g1.allyGuilds.remove(g2.guildId)
        g2.allyGuilds.remove(g1.guildId)
        g1.updateEntity()
        g2.updateEntity()
        plugin.logger.info("Guild ${g1.tag} is no longer ally with ${g2.tag}")
    }


    fun getGuildTagFor(sender: Player, receiver: Player): String {
        val playerGuild = plugin.manager.getGuildByMember(sender.uniqueId) ?: return ""
        if (playerGuild.getMember(receiver.uniqueId) != null) return plugin.config.guildTagFriendly.rep("%tag%", playerGuild.tag) + " "
        val otherGuild = getGuildByMember(receiver.uniqueId)
        return if (otherGuild == null || ! hasAlly(playerGuild, otherGuild))
            plugin.config.guildTagEnemy.rep("%tag%", playerGuild.tag) + " "
        else
            plugin.config.guildTagAlly.rep("%tag%", playerGuild.tag) + " "
    }

    fun openTopGuildMenu(player: Player) {
        val list = getTopGuildsSortedList()
        val menu = Menu(plugin.config.options)
        if (list.isEmpty()) return menu.open(player)
        if (list.size == 1) {
            menu.setItem(0, Item(buildTopItemFromConfig(list[0])))
            return menu.open(player)
        }
        val size = if (plugin.config.topGuildSize > list.size) list.size else plugin.config.topGuildSize
        0.rangeTo(size).forEach { menu.setItem(it, Item(buildTopItemFromConfig(list[it]))) }
        menu.open(player)
    }

    private fun buildTopItemFromConfig(guild: Guild): ItemStack {
        val item = ItemBuilder(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal.toShort()).buildItemStack()
        val meta = item.itemMeta as SkullMeta
        val lore = arrayListOf(*plugin.config.topGuildLore.toTypedArray())
        val leader = Bukkit.getOfflinePlayer(guild.leaderId)
        meta.owner = leader.name
        meta.displayName = plugin.config.topGuildName.rep("%tag%", guild.tag)
        lore.replaceAll {
            it
                    .rep("%tag%", guild.tag)
                    .rep("%name%", guild.name)
                    .rep("%leader%", leader.name)
                    .rep("%points%", getGuildPoints(guild).toString())
                    .rep("%kills%", getGuildKills(guild).toString())
                    .rep("%deaths%", getGuildDeaths(guild).toString())
                    .rep("%assists%", getGuildAssists(guild).toString())
                    .rep("%place%", getGuildPlace(guild).toString())
        }
        meta.lore = lore
        item.itemMeta = meta
        return item
    }


    fun getGuildProtectionTime(guild: Guild) = if (guild.createDate == guild.lastDestroyed) guild.createDate + plugin.config.guildStartProtectionTime else guild.lastDestroyed + plugin.config.guildAfterDestroyProtectionTime

    fun isGuildExpired(guild: Guild) = System.currentTimeMillis() > guild.expireTime

    fun isGuildProtected(guild: Guild) = getGuildProtectionTime(guild) > System.currentTimeMillis()

    fun canHaveMoreMembers(guild: Guild) = guild.membersSize >= guild.members.size

    fun broadcastGuild(guild: Guild, message: String) = guild.members.filter { it.offlinePlayer.isOnline }.forEach { it.offlinePlayer.player.sendMessage(message) }

    fun hasJoinItems(guild: Guild, inventory: Inventory): Boolean {
        val amount = guild.members.size * plugin.config.guildJoinPerMemberAmount
        val item = ItemStack(Material.getMaterial(plugin.config.guildJoinItemId), amount, plugin.config.guildJoinItemData)
        return Items.hasItem(inventory, item)
    }

    fun removeJoinItems(guild: Guild, inventory: Inventory) = Items.consumeItem(inventory, Material.getMaterial(plugin.config.guildJoinItemId), guild.members.size * plugin.config.guildJoinPerMemberAmount, plugin.config.guildJoinItemData)


    fun setGuildPvP(guild: Guild, boolean: Boolean) {
        plugin.logger.info("Guild ${guild.tag} setting pvp on $boolean")
        guild.options.pvp = boolean
        guild.updateEntity()
        plugin.logger.info("Guild ${guild.tag}  pvp has been set on $boolean")
    }

    fun setGuildBow(guild: Guild, boolean: Boolean) {
        plugin.logger.info("Guild ${guild.tag} setting bow pvp on $boolean")
        guild.options.bow = boolean
        guild.updateEntity()
        plugin.logger.info("Guild ${guild.tag} bow pvp has been set on $boolean")
    }

    private val buildItemHas
        get() = ItemBuilder(
                Material.getMaterial(plugin.config.guildMenuHasItemId),
                1,
                plugin.config.guildMenuHasItemData,
                plugin.config.guildMenuHasItemName
        ).buildItemStack()
    private val buildItemNeed
        get() = ItemBuilder(
                Material.getMaterial(plugin.config.guildMenuNeedItemId),
                1,
                plugin.config.guildMenuNeedItemData,
                plugin.config.guildMenuNeedItemName
        ).buildItemStack()
    private val buildItemUnknown get() = plugin.config.guildCreateItemUnknown.toItemStack()


    fun checkCreateGuildItems(player: Player) = if (plugin.config.guildCreateItems.isEmpty()) true else Items.hasItems(player.inventory, plugin.config.guildCreateItems.map { it.toItemStack() })

    fun removeCreateGuildItems(player: Player) {
        plugin.logger.info("Removing guild neededItems from player ${player.name}")
        plugin.config.guildCreateItems.map { it.toItemStack() }.forEach { Items.consumeItem(player.inventory, it.type, it.amount, it.durability) }
        plugin.logger.info("Removed all guild neededItems from player ${player.name}")
    }

    fun destroyEggGuild(player: Player, attacker: Guild, destroyed: Guild) {
        plugin.logger.info("Player ${player.name} from guild ${attacker.tag} attacking guild ${destroyed.tag}")
        Bukkit.broadcastMessage(plugin.config.messages.guildDestroyedByOther
                .rep("%destroyed%", destroyed.tag)
                .rep("%name%", player.name)
                .rep("%attacker% ", attacker.tag)
        )
        if (plugin.config.guildMaxLives > attacker.lives) {
            attacker.lives ++
            attacker.updateEntity()
            broadcastGuild(attacker, plugin.config.messages.yourGuildDestroyedOtherYouGotLife.rep("%tag%", destroyed.tag))
        } else {
            broadcastGuild(attacker, plugin.config.messages.yourGuildDestroyedOtherMaxLifes.rep("%tag%", destroyed.tag))
        }
        destroyed.lives --
        destroyed.lastDestroyed = System.currentTimeMillis()
        broadcastGuild(destroyed, plugin.config.messages.yourGuildHasBeenDestroyedLostLife.rep("%tag%", attacker.tag))
        if (destroyed.lives == 0) {
            Bukkit.broadcastMessage(plugin.config.messages.guildHasBeenDestroyed.rep("%tag%", destroyed.tag))
            removeGuild(destroyed)
            plugin.logger.info("Player ${player.name} from guild ${attacker.tag} destroyed guild ${destroyed.tag}")
            return
        }
        destroyed.updateEntity()
        plugin.logger.info("Player ${player.name} from guild ${attacker.tag} attacked guild ${destroyed.tag}")

    }


    fun showMenuWithGuildItems(player: Player) {
        val menuOptions = plugin.config.guildItemsMenuOptions
        val menu = Menu(menuOptions)
        val list = plugin.config.guildCreateItems
        val iterator = list.iterator()
        var slotItem = 9
        var slotHas = 18
        while (iterator.hasNext()) {
            if (slotItem == 18) break
            val next = iterator.next()
            if (! next.available) {
                val item = buildItemUnknown
                val meta = item.itemMeta
                val lore = ArrayList(meta.lore)
                lore.replaceAll { it.rep("%date%", Dates.formatData(next.longDate)) }
                meta.lore = lore
                item.itemMeta = meta
                menu.setItem(slotItem, Item(item))
                menu.setItem(slotHas, Item(buildItemNeed))
                slotItem ++
                slotHas ++
                continue
            }
            val item = next.toItemStack()
            val totalAmount = item.amount
            val hasAmount = Items.countItem(player.inventory, item)
            val needAmount = if (hasAmount >= totalAmount) 0 else totalAmount - hasAmount
            val paneItem: ItemStack
            val lore: ArrayList<String>
            if (needAmount <= 0) {
                paneItem = buildItemHas
                lore = ArrayList(plugin.config.guildMenuItemLoreHas)
            } else {
                paneItem = buildItemNeed
                lore = ArrayList(plugin.config.guildMenuItemLoreNeed)
            }
            lore.replaceAll {
                it.rep("%needAmount%", needAmount.toString()).rep("%hasAmount%", hasAmount.toString()).rep("%totalAmount%", totalAmount.toString())
            }
            val meta = paneItem.itemMeta
            meta.lore = lore
            paneItem.itemMeta = meta
            menu.setItem(slotItem, Item(item))
            menu.setItem(slotHas, Item(paneItem))
            slotItem ++
            slotHas ++
        }
        menu.open(player)
    }

    fun sendMessageToGuild(guild: Guild, message: String) = guild.members.map { it.offlinePlayer }.filter { it.isOnline }.map { it.player }.forEach { it.sendMessage(message) }

    private val guildExplodes = CacheBuilder
            .newBuilder()
            .expireAfterAccess(plugin.config.guildBlockBuildTime, TimeUnit.MILLISECONDS)
            .build<UUID, Long>()

    fun explodeOnTerrainGuild(guild: Guild) {
        if (! isGuildBlockedByExplode(guild)) sendMessageToGuild(guild, plugin.config.messages.guildBuildHasBeenBlockedByTnT)
        guildExplodes.put(guild.guildId, System.currentTimeMillis())
    }

    fun isGuildBlockedByExplode(guild: Guild) = guildExplodes.getIfPresent(guild.guildId) != null

    fun getGuildAvailableBuild(guild: Guild) = guildExplodes.getIfPresent(guild.guildId)?.plus(plugin.config.guildBlockBuildTime)
            ?: System.currentTimeMillis()

    fun getGuildPoints(guild: Guild): Int {
        val total = guild.members.map { it.uniqueId }.sumBy { RankingAPI.getRanking(it)?.points ?: 0 }
        return if (total > 0) total / guild.members.size else 0
    }


    fun getGuildKills(guild: Guild): Int {
        val total = guild.members.map { it.uniqueId }.sumBy { RankingAPI.getRanking(it)?.kills ?: 0 }
        return if (total > 0) total / guild.members.size else 0
    }


    fun getGuildDeaths(guild: Guild): Int {
        val total = guild.members.map { it.uniqueId }.sumBy { RankingAPI.getRanking(it)?.deaths ?: 0 }
        return if (total > 0) total / guild.members.size else 0
    }

    fun getGuildAssists(guild: Guild): Int {
        val total = guild.members.map { it.uniqueId }.sumBy { RankingAPI.getRanking(it)?.assists ?: 0 }
        return if (total > 0) total / guild.members.size else 0
    }

    fun getTopGuildsSortedList() = guilds.sortedBy { getGuildPoints(it) }

    fun getGuildPlace(guild: Guild) = getTopGuildsSortedList().indexOf(guild) + 1

    fun getGuildByPlace(place: Int): Guild? {
        val list = getTopGuildsSortedList()
        return if (place >= list.size) null else list[place]
    }


}