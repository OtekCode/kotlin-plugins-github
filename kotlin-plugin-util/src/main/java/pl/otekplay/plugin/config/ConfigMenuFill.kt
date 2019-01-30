package pl.otekplay.plugin.config

import org.bukkit.Material

class ConfigMenuFill(
        val filled:Boolean,
        val item:ConfigItem
){
    companion object {
        val DEFAULT = ConfigMenuFill(false, ConfigItem("",Material.STAINED_GLASS_PANE.id,1,4))
    }
}


