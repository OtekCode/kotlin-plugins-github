package pl.otekplay.protection

import com.google.common.cache.CacheBuilder
import com.google.common.cache.RemovalCause
import org.bukkit.Bukkit
import pl.otekplay.tag.TagAPI
import java.util.*
import java.util.concurrent.TimeUnit

class ProtectionManager(private val plugin: ProtectionPlugin) {
    private val protected = CacheBuilder.newBuilder()
            .expireAfterWrite(plugin.config.protectionTime, TimeUnit.MILLISECONDS)
            .removalListener<UUID, Long> {
                if (it.cause == RemovalCause.EXPIRED) {
                    val player = Bukkit.getPlayer(it.key) ?: return@removalListener
                    player.sendMessage(plugin.config.messages.youDontHaveProtection)
                }
            }
            .build<UUID, Long>()

    fun isProtected(uniqueId: UUID) = protected.getIfPresent(uniqueId) != null

    fun registerProtection(uniqueId: UUID) = protected.put(uniqueId, System.currentTimeMillis())

    fun getProtectionTimeEnd(uniqueId: UUID) = protected.getIfPresent(uniqueId)?.plus(plugin.config.protectionTime)
            ?: - 1

    fun disableProtection(uniqueId: UUID) = protected.invalidate(uniqueId).also {
        TagAPI.refresh(Bukkit.getPlayer(uniqueId) ?: return)
    }

}