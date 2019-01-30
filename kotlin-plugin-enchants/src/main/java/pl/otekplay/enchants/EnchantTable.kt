package pl.otekplay.enchants


import net.minecraft.server.v1_8_R3.*
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryEnchanting
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.enchantment.PrepareItemEnchantEvent
import java.util.*
import kotlin.collections.Map.Entry

class EnchantTable(playerinventory: PlayerInventory, private var world: World, private val position: BlockPosition) : Container() {
    var enchantSlots: InventorySubcontainer = object : InventorySubcontainer("Enchant", true, 2) {
        override fun getMaxStackSize(): Int {
            return 64
        }

        override fun update() {
            super.update()
            this@EnchantTable.a(this as IInventory)
        }
    }
    private val k = Random()
    var f: Int = 0
    var costs = IntArray(3)
    var h = intArrayOf(-1, -1, -1)
    private var bukkitEntity: CraftInventoryView? = null
    private val player: Player

    init {
        println("test enchant")
        enchantSlots.setItem(1, ItemStack(Items.DYE,3,EnumColor.BLUE.invColorIndex))
        this.f = playerinventory.player.cj()
        this.a(object : Slot(this.enchantSlots, 0, 15, 47) {
            override fun isAllowed(itemstack: ItemStack?): Boolean {
                return true
            }

            override fun getMaxStackSize(): Int {
                return 1
            }
        } as Slot)


        var i: Int = 0
        while (i < 3) {
            for (j in 0..8) {
                this.a(Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
            ++i
        }

        i = 0
        while (i < 9) {
            this.a(Slot(playerinventory, i, 8 + i * 18, 142))
            ++i
        }

        this.player = playerinventory.player.bukkitEntity as Player
    }

    override fun addSlotListener(icrafting: ICrafting) {
        super.addSlotListener(icrafting)
        icrafting.setContainerData(this, 0, this.costs[0])
        icrafting.setContainerData(this, 1, this.costs[1])
        icrafting.setContainerData(this, 2, this.costs[2])
        icrafting.setContainerData(this, 3, this.f and -16)
        icrafting.setContainerData(this, 4, -1)
        icrafting.setContainerData(this, 5, -1)
        icrafting.setContainerData(this, 6, -1)
    }

    override fun clickItem(i: Int, j: Int, k: Int, entityhuman: EntityHuman?): ItemStack? {
        if(i == 1) return null
        return super.clickItem(i, j, k, entityhuman)
    }

    override fun b() {
        super.b()

        for (i in this.listeners.indices) {
            val icrafting = this.listeners[i] as ICrafting
            icrafting.setContainerData(this, 0, this.costs[0])
            icrafting.setContainerData(this, 1, this.costs[1])
            icrafting.setContainerData(this, 2, this.costs[2])
            icrafting.setContainerData(this, 3, this.f and -16)
            icrafting.setContainerData(this, 4, -1)
            icrafting.setContainerData(this, 5, -1)
            icrafting.setContainerData(this, 6, -1)
        }

    }

    override fun a(iinventory: IInventory?) {
        if (iinventory === this.enchantSlots) {
            val itemstack = iinventory.getItem(0)
            var i: Int
            if (itemstack != null) {
                i = 0
                var j: Int = -1
                while (j <= 1) {
                    for (k in -1..1) {
                        if ((j != 0 || k != 0) && this.world.isEmpty(this.position.a(k, 0, j)) && this.world.isEmpty(this.position.a(k, 1, j))) {
                            if (this.world.getType(this.position.a(k * 2, 0, j * 2)).block === Blocks.BOOKSHELF) {
                                ++i
                            }

                            if (this.world.getType(this.position.a(k * 2, 1, j * 2)).block === Blocks.BOOKSHELF) {
                                ++i
                            }

                            if (k != 0 && j != 0) {
                                if (this.world.getType(this.position.a(k * 2, 0, j)).block === Blocks.BOOKSHELF) {
                                    ++i
                                }

                                if (this.world.getType(this.position.a(k * 2, 1, j)).block === Blocks.BOOKSHELF) {
                                    ++i
                                }

                                if (this.world.getType(this.position.a(k, 0, j * 2)).block === Blocks.BOOKSHELF) {
                                    ++i
                                }

                                if (this.world.getType(this.position.a(k, 1, j * 2)).block === Blocks.BOOKSHELF) {
                                    ++i
                                }
                            }
                        }
                    }
                    ++j
                }

                this.k.setSeed(this.f.toLong())

                j = 0
                while (j < 3) {
                    this.costs[j] = EnchantmentManager.a(this.k, j, i, itemstack)
                    this.h[j] = -1
                    if (this.costs[j] < j + 1) {
                        this.costs[j] = 0
                    }
                    println("reset j < 3")
                    ++j
                }
                if (!itemstack.v()) {
                    i = 0
                    while (i < 3) {
                        this.costs[i] = 0
                        ++i
                    }
                    println("reset itemstack v")
                    return
                }
                j = 0

                this.b()
            }
            else {
                i = 0
                while (i < 3) {
                    println("reset else")
                    this.costs[i] = 0
                    this.h[i] = -1
                    ++i
                }
            }
        }

    }

    override fun a(entityhuman: EntityHuman?, i: Int): Boolean {
        println("a")
        val itemstack = this.enchantSlots.getItem(0)

        val j = i + 1
         if (this.costs[i] <= 0 || itemstack == null || (entityhuman!!.expLevel < j || entityhuman.expLevel < this.costs[i]) && !entityhuman.abilities.canInstantlyBuild) {
            println("2")
            return false
        } else {
            println("c")
            if (!this.world.isClientSide) {
                val list: List<*> = this.a(itemstack, i, this.costs[i])

                val flag = itemstack.item === Items.BOOK
                val enchants: HashMap<Enchantment, Int> = HashMap()
                val var9 = list.iterator()

                while (true) {
                    println("d true")
                    if (!var9.hasNext()) {
                        println("d true next")
                        val item = CraftItemStack.asCraftMirror(itemstack)
                        val event = EnchantItemEvent(entityhuman.bukkitEntity as Player, this.bukkitView, this.world.world.getBlockAt(this.position.x, this.position.y, this.position.z), item, this.costs[i], enchants, i)
                        this.world.server.pluginManager.callEvent(event)
                        val level = event.expLevelCost
                        if (event.isCancelled || level > entityhuman.expLevel && !entityhuman.abilities.canInstantlyBuild || event.enchantsToAdd.isEmpty()) {
                            println("3")
                            return false
                        }

                        if (flag) {
                            itemstack.item = Items.ENCHANTED_BOOK
                        }

                        val var12 = event.enchantsToAdd.entries.iterator()

                        while (var12.hasNext()) {
                            val entry = var12.next() as Entry<*, *>

                            try {
                                if (flag) {
                                    val enchantId = (entry.key as Enchantment).id
                                    if (net.minecraft.server.v1_8_R3.Enchantment.getById(enchantId) != null) {
                                        val enchantment = WeightedRandomEnchant(net.minecraft.server.v1_8_R3.Enchantment.getById(enchantId), entry.value as Int)
                                        Items.ENCHANTED_BOOK.a(itemstack, enchantment)
                                    }
                                } else {
                                    item.addUnsafeEnchantment(entry.key as Enchantment, entry.value as Int)
                                }
                            } catch (var16: IllegalArgumentException) {
                            }

                        }
                        println("enchantDone")

                        entityhuman.enchantDone(j)
                        entityhuman.b(StatisticList.W)
                        this.enchantSlots.update()
                        this.f = entityhuman.cj()
                        this.a(this.enchantSlots as IInventory)
                        break
                    }

                    val obj = var9.next()
                    val instance = obj as WeightedRandomEnchant
                    enchants[Enchantment.getById(instance.enchantment.id)] = instance.level
                }

            }

            return true
        }
    }

    private fun a(itemstack: ItemStack, i: Int, j: Int): List<WeightedRandomEnchant> {
        this.k.setSeed((this.f + i).toLong())
        val list = EnchantmentManager.b(this.k, itemstack, j)
        if (itemstack.item === Items.BOOK && list != null && list.size > 1) {
            list.removeAt(this.k.nextInt(list.size))
        }

        return list
    }

    override fun b(entityhuman: EntityHuman) {
        super.b(entityhuman)
        if (!this.world.isClientSide) {
            for (i in 0 until this.enchantSlots.size) {
                if(i == 1) continue
                val itemstack = this.enchantSlots.splitWithoutUpdate(i)
                if (itemstack != null) {
                    entityhuman.drop(itemstack, false)
                }
            }
        }

    }

    override fun a(entityhuman: EntityHuman): Boolean {
        return if (!this.checkReachable) {
            true
        } else {
            if (this.world.getType(this.position).block !== Blocks.ENCHANTING_TABLE) false else entityhuman.e(this.position.x.toDouble() + 0.5, this.position.y.toDouble() + 0.5, this.position.z.toDouble() + 0.5) <= 64.0
        }
    }

    override fun b(entityhuman: EntityHuman?, i: Int): ItemStack? {
        var itemstack: ItemStack? = null
        val slot = this.c[i] as Slot
        if(i == 1) return null
        if (slot.hasItem()) {
            val itemstack1 = slot.item
            itemstack = itemstack1.cloneItemStack()
            if (i == 0) {
                if (!this.a(itemstack1, 2, 38, true)) {
                    return null
                }
            } else if (i == 1) {
                if (!this.a(itemstack1, 2, 38, true)) {
                    return null
                }
            } else if (itemstack1.item === Items.DYE && EnumColor.fromInvColorIndex(itemstack1.data) == EnumColor.BLUE) {
                if (!this.a(itemstack1, 1, 2, true)) {
                    return null
                }
            } else {
                if ((this.c[0] as Slot).hasItem() || !(this.c[0] as Slot).isAllowed(itemstack1)) {
                    return null
                }

                if (itemstack1.hasTag() && itemstack1.count == 1) {
                    (this.c[0] as Slot).set(itemstack1.cloneItemStack())
                    itemstack1.count = 0
                } else if (itemstack1.count >= 1) {
                    val clone = itemstack1.cloneItemStack()
                    clone.count = 1
                    (this.c[0] as Slot).set(clone)
                    --itemstack1.count
                }
            }

            if (itemstack1.count == 0) {
                slot.set(null as ItemStack?)
            } else {
                slot.f()
            }

            if (itemstack1.count == itemstack!!.count) {
                return null
            }

            slot.a(entityhuman, itemstack1)
        }

        return itemstack
    }

    override fun getBukkitView(): CraftInventoryView {
        return if (this.bukkitEntity != null) {
            this.bukkitEntity!!
        } else {
            val inventory = CraftInventoryEnchanting(this.enchantSlots)
            this.bukkitEntity = CraftInventoryView(this.player, inventory, this)
            this.bukkitEntity!!
        }
    }
}
