package pl.otekplay.plugin.config

data class ConfigInventoryArmor(
        val head:ConfigEnchantedItem?,
        val chest:ConfigEnchantedItem?,
        val legs:ConfigEnchantedItem?,
        val boots:ConfigEnchantedItem?
){

    fun toMap(): Map<Int, ConfigEnchantedItem> {
        val map = HashMap<Int,ConfigEnchantedItem>()
        if(head != null) map[3] = head
        if(chest != null) map[2] = chest
        if(legs != null) map[1] = legs
        if(boots != null) map[0] = boots
        return map
    }

    fun toArray() = arrayOf(boots,legs,chest,head)
}