package pl.otekplay.market.creator

import org.bukkit.Material
import pl.otekplay.market.MarketPlugin
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class CreatorManager(val plugin: MarketPlugin) {

    private val configs = HashMap<CreatorType, CreatorConfig>()


    init {

    }

    fun getConfig(type: CreatorType) = configs[type]


    private fun loadConfig(type: CreatorType) {
        configs[type] = plugin.loadConfig(CreatorConfig::class, type.config, "${type.name}Config")
    }

    fun load() {
        CreatorType.values().forEach { loadConfig(it) }
    }
}