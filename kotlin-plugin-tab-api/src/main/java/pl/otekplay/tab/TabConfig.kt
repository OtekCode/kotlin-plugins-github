package pl.otekplay.tab

import pl.otekplay.plugin.api.PluginConfiguration

class TabConfig:PluginConfiguration {
    val updateInterval = 30L
    val messageYouReloadedConfig = "Przeladowales tabliste!"
    val rows = generateList()

    companion object {
        fun generateList(): ArrayList<String> {
            val list = arrayListOf<String>()
            for(i in 1..80){
                list.add("slot$i")
            }
            return list
        }
    }
}