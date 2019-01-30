package pl.otekplay.tab

import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedGameProfile
import org.bukkit.entity.Player
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.wrappers.WrapperPlayServerPlayerInfo
import pl.otekplay.plugin.wrappers.WrapperPlayServerScoreboardTeam
import java.util.*
import kotlin.collections.HashMap

class TabManager(val plugin: TabPlugin) {
    private val map = TabMap()
    private val slots = HashMap<Int, TabSlot>()
    private val variables = HashMap<String, (Player) -> String>()

    companion object {
        private const val TABLIST_LATENCY = 100
        private val TABLIST_GAMEMODE = EnumWrappers.NativeGameMode.SURVIVAL
    }

    init {
        loadSlots()
        registerVariable("nickname") { p -> p.name }
    }

    fun loadSlots(){
        slots.clear()
        val mappedSlots = map.generateMap().sortedArray()
        for (index in mappedSlots.indices) {
            val profile = WrappedGameProfile(UUID.randomUUID(), mappedSlots[index])
            val slot = TabSlot(profile, index, plugin.config.rows[index])
            slots[index] = slot
        }
    }

    private fun replaceString(string: String, player: Player): String {
        var display = string
        for (variable in variables.filter { string.contains(it.key) }) {
            display = string.rep("%${variable.key}%", variable.value.invoke(player))
        }
        return display
    }

    private fun buildData(slot: TabSlot, player: Player) = PlayerInfoData(
            slot.profile,
            TABLIST_LATENCY,
            TABLIST_GAMEMODE,
            WrappedChatComponent.fromText(replaceString(slot.display, player))
    )

    private fun buildData(slot: TabSlot) = PlayerInfoData(
            slot.profile,
            TABLIST_LATENCY,
            TABLIST_GAMEMODE,
            WrappedChatComponent.fromText(slot.display)
    )

    private fun buildUpdatePacket(list: List<PlayerInfoData>): List<WrapperPlayServerPlayerInfo> {
        println("UpdatePacket")
        list.forEach {
            println("Name: ${it.profile.name}, Display: ${it.displayName}")
        }
        return list.map {
            WrapperPlayServerPlayerInfo().apply {
                EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME
                data = arrayListOf(it)
            }
        }
//        return WrapperPlayServerPlayerInfo().apply {
//            action = EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME
//            data = list
//        }
    }

    private fun buildRemovePacket(): List<WrapperPlayServerPlayerInfo> {
        return slots.values.map { buildData(it) }.map {
            WrapperPlayServerPlayerInfo().apply {
                EnumWrappers.PlayerInfoAction.REMOVE_PLAYER
                data = arrayListOf(it)
            }
        }
//        return WrapperPlayServerPlayerInfo().apply {
//            action = EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME
//            data = list
//        }
    }

    private fun buildCreatePacket(list: List<PlayerInfoData>): List<WrapperPlayServerPlayerInfo> {
        println("CreatePacket")
        list.forEach {
            println("Name: ${it.profile.name}, Display: ${it.displayName}")
        }
        list.sortedBy { it.profile.name }.forEachIndexed { index, data -> println("Index: $index, Name: ${data.profile.name}, Display: ${data.displayName} ") }

        return list.map {
            WrapperPlayServerPlayerInfo().apply {
                EnumWrappers.PlayerInfoAction.ADD_PLAYER
                data = arrayListOf(it)
            }
        }
//        return WrapperPlayServerPlayerInfo().apply {
//            action = EnumWrappers.PlayerInfoAction.ADD_PLAYER
//            data = list
//        }
    }

    fun create(player: Player) {
        plugin.logger.info("Create tablist for player ${player.name}")
        buildCreatePacket(slots.values.map { buildData(it) }).forEach { it.sendPacket(player)}
    }

    fun update(player: Player) {
        plugin.logger.info("Update tablist for player ${player.name}")
        buildRemovePacket().forEach { it.sendPacket(player) }
        buildUpdatePacket(slots.values.map { buildData(it, player) }).forEach { it.sendPacket(player)}
    }

    fun registerVariable(name: String, listener: (Player) -> String) {
        plugin.logger.info("Register tab variable $name")
        variables[name] = listener
    }

}