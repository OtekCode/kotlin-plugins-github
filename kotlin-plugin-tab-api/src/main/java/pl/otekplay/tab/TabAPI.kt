package pl.otekplay.tab

import org.bukkit.entity.Player

object TabAPI {
    internal lateinit var plugin: TabPlugin

    fun registerVariable(name: String, listener: (Player) -> String) = plugin.manager.registerVariable(name,listener)

}