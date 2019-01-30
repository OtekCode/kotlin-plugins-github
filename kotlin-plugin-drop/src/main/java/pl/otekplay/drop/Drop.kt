package pl.otekplay.drop

import org.bukkit.Material
import org.bukkit.entity.Player
import pl.otekplay.drop.config.DropItem
import pl.otekplay.drop.config.DropResult
import pl.otekplay.drop.config.bonus.Bonus
import pl.otekplay.drop.config.bonus.Fortune
import pl.otekplay.drop.config.bonus.Perm
import pl.otekplay.drop.config.options.Info
import pl.otekplay.drop.config.options.Time
import pl.otekplay.plugin.util.Randoms

class Drop(val info: Info,
           val item: DropItem,
           val time: Time,
           val fortunes: List<Fortune>,
           val turbo: Bonus,
           val perms: List<Perm>
) {

    private fun getResult(fortune: Fortune?, perm: Perm?, hasTurbo: Boolean): DropResult {
        //DropExp
        val dropExp = info.dropExp
        val fortuneDropExp = fortune?.dropExp ?: 0
        val turboDropExp = if (hasTurbo) turbo.dropExp else 0
        val permDropExp = perm?.dropExp ?: 0
        val totalDropExp = dropExp + fortuneDropExp + turboDropExp + permDropExp

        //PlayerExp
        val playerExp = info.playerExp
        val fortunePlayerExp = fortune?.playerExp ?: 0
        val turboPlayerExp = if (hasTurbo) turbo.playerExp else 0
        val permPlayerExp = perm?.playerExp ?: 0
        val totalPlayerExp = playerExp + fortunePlayerExp + turboPlayerExp + permPlayerExp

        //Amount
        val amount = info.amount.getValue()
        val fortuneAmount = fortune?.amount ?: 0
        val turboAmount = if (hasTurbo) turbo.amount else 0
        val permAmount = perm?.amount ?: 0
        val totalAmount = amount + fortuneAmount + turboAmount + permAmount

        return DropResult(info.name, totalDropExp, totalPlayerExp, item.generateItem(totalAmount))
    }

    fun isBlockedByUser(drop: DropUser) = if (item.enabled) drop.isDisabled(Material.getMaterial(item.id)) else false

    fun isDropped(player: Player, user: DropUser): DropResult? {
        val fortune = fortunes.filter { it.enabled }
                .singleOrNull { it.checkValidPickAxe(player.itemInHand) }
        val chance = info.chance
        val hasTurbo = user.hasTurbo
        val fortuneChance = fortune?.chance ?: 0.0
        val turboChance = if (hasTurbo) turbo.chance else 0.0
        val perm = perms.filter { it.enabled && it.hasPermission(player) }.maxBy { it.priority }
        val permChance = perm?.chance ?: 0.0
        val dropped = Randoms.getChance(chance + fortuneChance + turboChance + permChance)
        return if (dropped) getResult(fortune, perm, hasTurbo) else null
    }



}