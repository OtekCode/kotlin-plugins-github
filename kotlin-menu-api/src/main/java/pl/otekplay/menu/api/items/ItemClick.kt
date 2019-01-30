package pl.otekplay.menu.api.items

import org.bukkit.entity.Player

@FunctionalInterface
interface ItemClick {
    fun click(player: Player)
}