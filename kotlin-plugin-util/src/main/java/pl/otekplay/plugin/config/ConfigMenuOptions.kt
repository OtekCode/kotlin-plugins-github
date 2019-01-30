package pl.otekplay.plugin.config

class ConfigMenuOptions(
        val title:String,
        val rows:Int,
        val filled:ConfigMenuFill
){
    companion object {
        val DEFAULT = ConfigMenuOptions("Default",6, ConfigMenuFill(false, ConfigItem.DEFAULT))
    }
}