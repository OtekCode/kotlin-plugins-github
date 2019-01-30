package pl.otekplay.magicegg

import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.util.Randoms

class MagicEgg(
        val name: String,
        val speed:Double,
        val result: ConfigItem,
        private val drops: ArrayList<ConfigEnchantedItem>
){


    fun randomItem(): ConfigEnchantedItem? {
        if(drops.size == 0) return null
        val randomNum = Randoms.getRandomInt(0,drops.size-1)
        return drops[randomNum]
    }
}