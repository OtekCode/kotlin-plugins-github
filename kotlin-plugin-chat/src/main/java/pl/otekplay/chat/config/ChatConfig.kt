package pl.otekplay.chat.config

import pl.otekplay.plugin.api.PluginConfiguration

class ChatConfig : PluginConfiguration {
    var delayTimeChat = 30000L
    var chatLocked = false
    val messages = ChatMessages()
    val formatChat = "%nickname%: %message%"
    val formatMessageSend = "&7Wyslales wiadomosc do &6%receiver% &7- &6%message%"
    val formatMessageReceive = "&6%sender% &7do Ciebie: &6%message%"
    val formatBroadcast = "&7&l[NEWS] &6%message%"

    val permissionChatDelayBypass = "chat.bypass.delay"
    val permissionChatLockedBypass = "chat.bypass.locked"

    val customFormats = listOf(
            ConfigChatFormat(0, "chat.format.player", "%nickname%: %message%"),
            ConfigChatFormat(1, "chat.format.vip", "[VIP] %nickname%: %message%"),
            ConfigChatFormat(2, "chat.format.svip", "[SVIP] %nickname%: %message%"),
            ConfigChatFormat(3, "chat.format.helper", "[Helper] %nickname%: %message%"),
            ConfigChatFormat(4, "chat.format.mod", "[Mod] %nickname%: %message%"),
            ConfigChatFormat(5, "chat.format.admin", "[Admin] %nickname%: %message%")
    )

    
}