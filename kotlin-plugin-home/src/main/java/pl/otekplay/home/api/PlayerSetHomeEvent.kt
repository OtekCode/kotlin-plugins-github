package pl.otekplay.home.api

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerSetHomeEvent(private val player: Player, var location: Location) : Event(), Cancellable {
    private var cancel: Boolean = false
    override fun setCancelled(bool: Boolean) {
        cancel = bool
    }

    override fun isCancelled() = cancel

    override fun getHandlers() = getHandlerList()

    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }

}



