package pl.otekplay.friend

import pl.otekplay.friend.commands.*
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation(name = "FriendPlugin", dependency = ["TagPlugin"])
class FriendPlugin(pluginLoader: PluginLoader,
                   annotation: PluginAnnotation,
                   logger: Logger) : Plugin(pluginLoader, annotation, logger){

    lateinit var friendListManager: FriendListManager

    override fun onEnable() {
        friendListManager = FriendListManager(this)
        registerListener(FriendListListener(this))
        registerPacketListener(FriendListPacketListener(this))
        registerCommand(FriendAcceptCommand(this))
        registerCommand(FriendInviteCommand(this))
        registerCommand(FriendListCommand(this))
        registerCommand(FriendRemoveCommand(this))
        registerCommand(FriendUninviteCommand(this))
    }
}