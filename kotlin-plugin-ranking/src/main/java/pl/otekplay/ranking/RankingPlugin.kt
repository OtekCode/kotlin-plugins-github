package pl.otekplay.ranking

import org.bukkit.Bukkit
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.tab.TabAPI
import java.util.logging.Logger

@PluginAnnotation(name = "RankingPlugin", dependency = ["DatabasePlugin", "TabPlugin", "FightPlugin", "ChatPlugin", "HistoryPlugin"])
class RankingPlugin(pluginLoader: PluginLoader, annotation: PluginAnnotation, logger: Logger) : Plugin(pluginLoader, annotation, logger) {
    lateinit var config: RankingConfig
    lateinit var manager: RankingManager

    override fun onEnable() {
        RankingAPI.plugin = this

        config = loadConfig(RankingConfig::class)
        manager = RankingManager(this)

        registerListener(RankingListener(this))
        registerCommand(RankingCommands(this))
        registerVariables()
        logger.info("Ranking API has been initialized!")


    }

    private fun registerVariables() {
        TabAPI.registerVariable("points") { p -> RankingAPI.getRanking(p.uniqueId)?.points.toString() }
        TabAPI.registerVariable("kills") { p -> RankingAPI.getRanking(p.uniqueId)?.kills.toString() }
        TabAPI.registerVariable("deaths") { p -> RankingAPI.getRanking(p.uniqueId)?.deaths.toString() }
        TabAPI.registerVariable("assists") { p -> RankingAPI.getRanking(p.uniqueId)?.assists.toString() }
        TabAPI.registerVariable("place") { p -> RankingAPI.getPlace(p.uniqueId).toString() }
        for (int: Int in 1..20) {
            TabAPI.registerVariable("top$int") { p ->
                val ranking = RankingAPI.getRanking(int - 1) ?: return@registerVariable "Brak"
                return@registerVariable Bukkit.getOfflinePlayer(ranking.uniqueId).name ?: "Brak"
            }
        }
    }

    fun reloadConfig() {
        config = loadConfig(RankingConfig::class)
    }

}

fun main(args: Array<String>) {

}