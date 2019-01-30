package pl.otekplay.guild.holders

import org.bukkit.Bukkit
import java.util.*

data class GuildMember(
        val uniqueId: UUID,
        val joinTime: Long
){
   val offlinePlayer get() =  Bukkit.getOfflinePlayer(uniqueId)!!
}