package pl.otekplay.bans

import pl.otekplay.database.DatabaseEntity
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates

data class Ban(
        val entry: String,
        val type: BanType,
        private val created: Long,
        private val time: Long,
        private val reason: String,
        private val source: String
) : DatabaseEntity() {
    override val id: String get() = entry
    override val key: String get() = "entry"
    override val collection: String get() = "bans"
    val expireTime get() = created + time
    val expired get() = if (time == -1L) false else System.currentTimeMillis() > expireTime

    fun replaceString(string: String) = string
                    .rep("%source%", source)
                    .rep("%entry%", entry)
                    .rep("%type%", type.name)
                    .rep("%reason%", reason)
                    .rep("%created%", Dates.formatData(created))
                    .rep("%expired%", Dates.formatData(expireTime))
}