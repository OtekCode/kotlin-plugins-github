package pl.otekplay.enchants

import net.minecraft.server.v1_8_R3.*
import org.bukkit.Location
import java.util.*


class TileEnchantTable(val location: Location) : TileEntity(), IUpdatePlayerListBox, ITileEntityContainer {
    var a: Int = 0
    var f: Float = 0.toFloat()
    var g: Float = 0.toFloat()
    var h: Float = 0.toFloat()
    var i: Float = 0.toFloat()
    var j: Float = 0.toFloat()
    var k: Float = 0.toFloat()
    var l: Float = 0.toFloat()
    var m: Float = 0.toFloat()
    var n: Float = 0.toFloat()
    private var p: String? = null

    override fun b(var1: NBTTagCompound) {
        super.b(var1)
        if (this.hasCustomName()) {
            var1.setString("CustomName", this.p!!)
        }

    }

    override fun a(var1: NBTTagCompound) {
        super.a(var1)
        if (var1.hasKeyOfType("CustomName", 8)) {
            this.p = var1.getString("CustomName")
        }

    }

    override fun c() {
        this.k = this.j
        this.m = this.l
        val var1 = this.world.findNearbyPlayer((this.position.x.toFloat() + 0.5f).toDouble(), (this.position.y.toFloat() + 0.5f).toDouble(), (this.position.z.toFloat() + 0.5f).toDouble(), 3.0)
        if (var1 != null) {
            val var2 = var1.locX - (this.position.x.toFloat() + 0.5f).toDouble()
            val var4 = var1.locZ - (this.position.z.toFloat() + 0.5f).toDouble()
            this.n = MathHelper.b(var4, var2).toFloat()
            this.j += 0.1f
            if (this.j < 0.5f || o.nextInt(40) == 0) {
                val var6 = this.h

                do {
                    this.h += (o.nextInt(4) - o.nextInt(4)).toFloat()
                } while (var6 == this.h)
            }
        } else {
            this.n += 0.02f
            this.j -= 0.1f
        }

        while (this.l >= 3.1415927f) {
            this.l -= 6.2831855f
        }

        while (this.l < -3.1415927f) {
            this.l += 6.2831855f
        }

        while (this.n >= 3.1415927f) {
            this.n -= 6.2831855f
        }

        while (this.n < -3.1415927f) {
            this.n += 6.2831855f
        }

        var var7: Float
        var7 = this.n - this.l
        while (var7 >= 3.1415927f) {
            var7 -= 6.2831855f
        }

        while (var7 < -3.1415927f) {
            var7 += 6.2831855f
        }

        this.l += var7 * 0.4f
        this.j = MathHelper.a(this.j, 0.0f, 1.0f)
        ++this.a
        this.g = this.f
        var var8 = (this.h - this.f) * 0.4f
        val var9 = 0.2f
        var8 = MathHelper.a(var8, -var9, var9)
        this.i += (var8 - this.i) * 0.9f
        this.f += this.i
    }

    override fun getName(): String? {
        return if (this.hasCustomName()) this.p else "container.enchant"
    }

    override fun hasCustomName(): Boolean {
        return this.p != null && this.p!!.isNotEmpty()
    }


    override fun getScoreboardDisplayName(): IChatBaseComponent {
        return if (this.hasCustomName()) ChatComponentText(this.name) else ChatMessage(this.name, *arrayOfNulls(0))
    }

    override fun createContainer(var1: PlayerInventory, var2: EntityHuman): Container {
        return EnchantTable(var1, var2.world, BlockPosition(location.x,location.y,location.z))
    }

    override fun getContainerName(): String {
        return "minecraft:enchanting_table"
    }

    companion object {
        private val o = Random()
    }
}
