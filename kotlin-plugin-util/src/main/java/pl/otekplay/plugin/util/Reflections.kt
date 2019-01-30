package pl.otekplay.plugin.util

import net.minecraft.server.v1_8_R3.Packet
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

object Reflections {
    private val serverVersion = getServerVersion()

    private fun getServerVersion(): String {
        val server = Bukkit.getServer().javaClass
        if (server.simpleName != "CraftServer") {
            return "."
        }
        return if (server.name == "org.bukkit.craftbukkit.CraftServer") {
            "."
        } else {
            val version = server.name.substring("org.bukkit.craftbukkit".length)
            version.substring(0, version.length - "CraftServer".length)
        }
    }

    private fun nms(className: String) = "net.minecraft.server$serverVersion$className"


    fun nmsClass(className: String): Class<*> {
        return Class.forName(nms(className))
    }

    private fun obc(className: String) = "org.bukkit.craftbukkit$serverVersion$className"


    fun obcClass(className: String): Class<*> {
        return Class.forName(obc(className))
    }

    fun sendPacket(player: Player, packet: Packet<*>) {
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)

    }
}