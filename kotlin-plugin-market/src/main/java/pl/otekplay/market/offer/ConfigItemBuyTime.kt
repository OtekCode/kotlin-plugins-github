package pl.otekplay.market.offer

import org.bukkit.Material
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuItem

class ConfigItemBuyTime(slot:Int,
                        name: String,
                        lore: List<String>,
                        val buyTime: BuyTime
) : ConfigMenuItem(slot,ConfigItem(name, Material.WATCH.id, 1, 0, lore))