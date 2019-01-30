package pl.otekplay.bans

import pl.otekplay.plugin.api.PluginConfiguration

class BanConfig : PluginConfiguration {

    val messages = BanMessages()

    val formatLoginDisallowBanned = arrayListOf(
            "Typ: %type%",
            "Nadany: %created%",
            "Wygasa: %expired%",
            "Od: %source%",
            "Powod:",
            "%reason%"
    )





}