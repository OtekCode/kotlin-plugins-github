package pl.otekplay.backup

import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigInventoryArmor

data class Backup(
        val id:String,
        val time:Long,
        val type:BackupType,
        val contents:Map<Int,ConfigEnchantedItem> = HashMap(),
        val armor:ConfigInventoryArmor,
        val chest: Map<Int,ConfigEnchantedItem> = HashMap()



)