package pl.otekplay.guild.config

import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.util.Dates

class GuildConfigItem(name: String,
                      id: Int,
                      amount: Int,
                      data: Short,
                      lore: List<String>,
                      private val date:String
) : ConfigItem(name, id, amount, data, lore){

    val longDate get() =  Dates.parseData(date).time

    val available get() =  System.currentTimeMillis() > longDate
}

