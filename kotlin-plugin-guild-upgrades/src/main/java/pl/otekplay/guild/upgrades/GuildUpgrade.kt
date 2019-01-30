package pl.otekplay.guild.upgrades

import pl.otekplay.guild.holders.Guild

interface GuildUpgrade {

    fun upgrade(guild: Guild, value:Int)
}