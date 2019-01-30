package pl.otekplay.drop.config.options

import org.bukkit.block.Biome

class Info(val name: String,
           val chance: Double,
           val dropExp: Int,
           val playerExp: Int,
           val permission: String,
           val breakIds: Array<Int>,
           val height: Height,
           val pickAxe: PickAxe,
           val amount: MinMax,
           val biomes: List<Biome>
)
