package pl.otekplay.plugin

import org.bukkit.Material
import org.bukkit.entity.Player
import pl.otekplay.plugin.util.Reflections
import pl.otekplay.plugin.util.Strings

fun String.rep(search: String, replacement: String) = Strings.replace(this, search, replacement)

fun Player.sendActionBar(text: String) {
    val iChat = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer.a("{\"text\":\"$text\"}")
    val packet = net.minecraft.server.v1_8_R3.PacketPlayOutChat(iChat, 2.toByte())
    Reflections.sendPacket(player, packet)
}

fun Material.bukkitName(): String = name.toLowerCase().split("_").map { it.capitalize() }.joinToString { it }.rep(",", "")

