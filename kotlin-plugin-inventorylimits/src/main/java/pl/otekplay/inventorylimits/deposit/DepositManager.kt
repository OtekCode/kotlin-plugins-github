package pl.otekplay.inventorylimits.deposit

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.inventorylimits.InventoryLimitPlugin
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.util.Items
import pl.otekplay.plugin.rep
import java.util.*

class DepositManager(val plugin: InventoryLimitPlugin) {
    private val deposits = hashMapOf<UUID, Deposit>()

    init {
        DatabaseAPI.loadAll<Deposit>("deposits", {
            it.forEach {
                deposits[it.uniqueId] = it
            }
            plugin.logger.info("Loaded ${deposits.size} deposits.")
        })
    }

    fun getDeposit(uniqueId: UUID) = deposits[uniqueId]

    fun createDeposit(uniqueId: UUID) = deposits.put(uniqueId, Deposit(uniqueId).also { it.insertEntity() })

    fun checkInventoryPlayer(player: Player) {
        plugin.taskLaterSync(object : BukkitRunnable() {
            override fun run() {
                val inventory = player.inventory
                plugin.config.limits.forEach {
                    val material = Material.getMaterial(it.id)
                    val amount = Items.countItem(inventory, material, it.data)
                    if (amount > it.limit) {
                        val consume = amount - it.limit
                        val deposit = getDeposit(player.uniqueId) ?: return
                        deposit.addDeposit(it.id, it.data, consume)
                        deposit.updateEntity()
                        Items.consumeItem(inventory, material, consume, it.data)
                        player.sendMessage(it.message
                                .rep("%consume%", consume.toString())
                                .rep("%limit%", it.limit.toString())
                        )
                    }
                }
            }

        }, 1)

    }


    fun getItemFromDeposit(player: Player, id: Int, data: Short, limit: Int) {
        val playerDeposit = getDeposit(player.uniqueId)
                ?: return player.sendMessage(plugin.config.messages.cantPullNoItems)
        val deposit = playerDeposit.getDeposit(id, data)
                ?: return player.sendMessage(plugin.config.messages.cantPullNoItems)
        if (! deposit.canPull) return player.sendMessage(plugin.config.messages.cantPullNoItems)
        val material = Material.getMaterial(id)
        val amount = Items.countItem(player.inventory, material, data)
        if (amount >= limit) return player.sendMessage(plugin.config.messages.youHaveLimitThisInInventory)
        player.inventory.addItem(ItemStack(material, 1, data))
        //Items.addItem(player, ItemStack(material, 1, data))
        player.sendMessage(plugin.config.messages.pullOneItem.rep("%total%", (deposit.pull-1).toString()))
        playerDeposit.updateEntity()
        openDepositMenu(player)
    }

    fun openDepositMenu(player: Player) {
        val menu = Menu(plugin.config.menuOptions)
        plugin.config.limits.forEach {
            val id = it.id
            val material = Material.getMaterial(id)
            val limit = it.limit
            val data = it.data
            val deposit = getDeposit(player.uniqueId)?.getDeposit(id,data)?.amount ?: 0
            val item = it.iconMenu
            val stack = item.item.toItemStack()
            val meta = stack.itemMeta
            if (meta.hasLore()) {
                meta.lore = meta.lore.map {
                    it.rep("%deposit%",deposit.toString()).rep("%limit%", limit.toString()).rep("%amount%", Items.countItem(player.inventory, material, data).toString())
                }
            }
            stack.itemMeta = meta
            menu.setItem(item.slot, Item(stack, object : ItemClick {
                override fun click(player: Player) {
                    getItemFromDeposit(player, it.id, it.data, it.limit)
                }
            }))
        }

        menu.open(player)
    }
}