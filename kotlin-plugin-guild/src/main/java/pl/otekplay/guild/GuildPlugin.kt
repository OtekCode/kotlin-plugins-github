package pl.otekplay.guild

import pl.otekplay.guild.commands.*
import pl.otekplay.guild.commands.admin.ReloadCommand
import pl.otekplay.guild.commands.admin.TeleportCommand
import pl.otekplay.guild.holders.GuildManager
import pl.otekplay.guild.listeners.*
import pl.otekplay.guild.tasks.GuildExpireTask
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import pl.otekplay.tab.TabAPI
import java.util.logging.Logger

@PluginAnnotation(
        name = "GuildPlugin",
        dependency = ["DatabasePlugin","TabPlugin", "TeleportPlugin", "TagPlugin", "ChatPlugin", "RankingPlugin"]
)
class GuildPlugin(pluginLoader: PluginLoader,
                  annotation: PluginAnnotation,
                  logger: Logger
) : Plugin(pluginLoader, annotation, logger
) {
    lateinit var config: GuildConfig
    lateinit var manager: GuildManager

    override fun onEnable() {
        config = loadConfig(GuildConfig::class)
        manager = GuildManager(this)
        registerCommands()
        registerListeners()
        registerTasks()
        registerVariables()
        GuildAPI.plugin = this
        logger.info("GuildAPI has been initialized.")
    }

    internal fun reloadConfig() {
        config = loadConfig(GuildConfig::class)
    }

    private fun registerCommands() {
        logger.info("Registering commands...")
        registerCommand(InviteCommand(this))
        registerCommand(CreateCommand(this))
        registerCommand(DeleteCommand(this))
        registerCommand(InfoCommand(this))
        registerCommand(ListCommand(this))
        registerCommand(JoinCommand(this))
        registerCommand(LeaveCommand(this))
        registerCommand(RemoveCommand(this))
        registerCommand(ExpireCommand(this))
        registerCommand(SetHomeCommand(this))
        registerCommand(HomeCommand(this))
        registerCommand(AllyCommand(this))
        registerCommand(WarCommand(this))
        registerCommand(PvPCommand(this))
        registerCommand(BowCommand(this))
        registerCommand(ItemsCommand(this))
        registerCommand(BuildCommand(this))
        registerCommand(TopCommand(this))
        registerCommand(LeaderCommand(this))
        registerCommand(ReloadCommand(this))
        registerCommand(TeleportCommand(this))
    }

    private fun registerListeners() {
        logger.info("Registering listeners...")
        registerListener(PistonListeners(this))
        registerListener(BreakListeners(this))
        registerListener(PlaceListeners(this))
        registerListener(FightListeners(this))
        registerListener(PlayerListeners(this))
        registerListener(TagListener(this))
        registerListener(ChatListener(this))
        registerListener(MoveListener(this))
        registerListener(ExplodeListener(this))
    }


    private fun registerVariables() {
        logger.info("Registering variables..")
        TabAPI.registerVariable("gpoints") { p ->
            val guild = GuildAPI.getGuildByMember(p.uniqueId) ?: return@registerVariable ""
            GuildAPI.getGuildPoints(guild).toString()
        }
        TabAPI.registerVariable("gkills") { p ->
            val guild = GuildAPI.getGuildByMember(p.uniqueId) ?: return@registerVariable ""
            GuildAPI.getGuildKills(guild).toString()
        }
        TabAPI.registerVariable("gdeaths") { p ->
            val guild = GuildAPI.getGuildByMember(p.uniqueId) ?: return@registerVariable ""
            GuildAPI.getGuildDeaths(guild).toString()
        }
        TabAPI.registerVariable("gassists") { p ->
            val guild = GuildAPI.getGuildByMember(p.uniqueId) ?: return@registerVariable ""
            GuildAPI.getGuildAssists(guild).toString()
        }
        TabAPI.registerVariable("gplace") { p ->
            val guild = GuildAPI.getGuildByMember(p.uniqueId) ?: return@registerVariable ""
            GuildAPI.getGuildPlace(guild).toString()
        }
        TabAPI.registerVariable("gleader") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.name ?: "" }
        TabAPI.registerVariable("glives") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.lives.toString() }
        for (int: Int in 1..20) {
            TabAPI.registerVariable("gtop$int") { p -> GuildAPI.getGuildByPlace(int - 1)?.tag ?: "Brak" }
        }
        TabAPI.registerVariable("gtag") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.tag ?: "" }
        TabAPI.registerVariable("gcuboid") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.cuboidSize.toString().rep("null", "") }
        TabAPI.registerVariable("gslots") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.membersSize.toString().rep("null", "") }
        TabAPI.registerVariable("gally") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.allyGuilds?.size.toString().rep("null", "") }
        TabAPI.registerVariable("gmembers") { p -> GuildAPI.getGuildByMember(p.uniqueId)?.members?.size.toString().rep("null", "") }
        TabAPI.registerVariable("gexpire") { p ->
            val guild = GuildAPI.getGuildByMember(p.uniqueId) ?: return@registerVariable ""
            return@registerVariable Dates.formatData(guild.expireTime)
        }
    }

    private fun registerTasks() {
        logger.info("Registering tasks...")
        taskTimerSync(GuildExpireTask(this), config.guildTaskTimerExpire)
    }

}