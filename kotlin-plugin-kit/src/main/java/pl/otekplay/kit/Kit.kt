package pl.otekplay.kit

import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.util.Dates

data class Kit(val name: String,
               val permission: String,
               val startDate: String,
               val delayTime: Long,
               val menuItem:ConfigMenuItem,
               val list: ArrayList<ConfigEnchantedItem>
){

    val startDateLong get() = Dates.parseData(startDate).time
}