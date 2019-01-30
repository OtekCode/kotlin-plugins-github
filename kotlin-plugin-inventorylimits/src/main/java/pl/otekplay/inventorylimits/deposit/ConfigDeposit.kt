package pl.otekplay.inventorylimits.deposit

import pl.otekplay.plugin.config.ConfigMenuItem


data class ConfigDeposit(val id:Int,val data:Short,val limit:Int,val iconMenu:ConfigMenuItem,val message:String)