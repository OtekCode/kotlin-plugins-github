package pl.otekplay.ranking

import pl.otekplay.plugin.api.PluginConfiguration

class RankingConfig : PluginConfiguration {
    val messages = RankingMessages()
    val menuTopPoints = ConfigTopMenu()
    val menuTopKills = ConfigTopMenu()
    val menuTopDeaths = ConfigTopMenu()
    val menuTopAssists = ConfigTopMenu()
    val rankingStartPoints = 1000
    val minPercentToValidAssist = 20.0
}

