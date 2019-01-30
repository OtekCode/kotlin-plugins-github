package pl.otekplay.mysterybox

import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.util.Randoms

class MysteryBox(
        val name: String,
        val item: ConfigItem,
        private val drops: ArrayList<ConfigEnchantedItem>,
        val message: String
){

    fun randomItem(): ConfigEnchantedItem? {
        if(drops.size == 0) return null
        val randomNum = Randoms.getRandomInt(0,drops.size-1)
        return drops[randomNum]
    }
}