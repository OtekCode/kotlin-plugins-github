package pl.otekplay.tools.system.explode

import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.tools.ToolsManager
import pl.otekplay.tools.ToolsPlugin

class ExplodeManager(plugin: ToolsPlugin) : ToolsManager(plugin) {
    override fun registerVariables() {
    }

    override fun registerCommands() {
        plugin.registerCommand(ExplodeCommand(plugin))
    }

    override fun registerListeners() {
        plugin.registerListener(ExplodeListener(this))
    }

    fun isExplodeBlocked(): Boolean {
        val time = Dates.dateInt()
        val from = config.explosionStartHour.rep(":", "").toInt()
        val to = plugin.config.explosionEndHour.rep(":", "").toInt()
        return Dates.isBeetwenHours(from, to, time)
    }

}