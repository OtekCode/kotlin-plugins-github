package pl.otekplay.tag

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerTagEvent(
        val player: Player,
        val requester: Player,
        var canSee:Boolean,
        var prefix: String,
        var suffix: String
) : Event() {
    override fun getHandlers() = getHandlerList()

    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}