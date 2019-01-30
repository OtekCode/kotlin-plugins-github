package pl.otekplay.protection

import pl.otekplay.plugin.api.PluginConfiguration
import java.util.concurrent.TimeUnit


class ProtectionConfig:PluginConfiguration {
    val protectionTime = TimeUnit.MINUTES.toMillis(30)
    val protectionTag = "[OCHRONA]"
    val messages = ProtectionMessages()
}