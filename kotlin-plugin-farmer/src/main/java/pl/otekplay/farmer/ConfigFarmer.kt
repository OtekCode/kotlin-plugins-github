package pl.otekplay.farmer

import pl.otekplay.plugin.config.ConfigRecipe


data class ConfigFarmer(
        val name:String,
        val buildId:Int,
        val buildTime: Long,
        val buildLimit: Int,
        val buildDown:Boolean,
        val stopOnBedrock:Boolean,
        val stopOnBlock:Boolean,
        val recipe: ConfigRecipe
)