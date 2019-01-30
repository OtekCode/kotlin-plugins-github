package pl.otekplay.drop.config

import pl.otekplay.drop.Drop
import pl.otekplay.drop.config.bonus.Bonus
import pl.otekplay.drop.config.bonus.Fortune
import pl.otekplay.drop.config.bonus.Perm
import pl.otekplay.drop.config.options.Info
import pl.otekplay.drop.config.options.Time

class DropHolder(val info: Info,
                 private val item: DropItem,
                 private val time: Time,
                 private val fortunes: List<Fortune>,
                 private val turbo: Bonus,
                 private val perms: List<Perm>
) {


    fun toLoaded(): Drop {
        return Drop(info, item, time, fortunes, turbo, perms)
    }

}