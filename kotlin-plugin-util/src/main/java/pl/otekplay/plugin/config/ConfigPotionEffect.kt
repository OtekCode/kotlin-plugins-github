package pl.otekplay.plugin.config

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ConfigPotionEffect(val id: Int, val level: Int, val seconds: Int) {


    fun toPotionEffect() = PotionEffect(PotionEffectType.getById(id), seconds * 20, level)
}