package pl.otekplay.generator

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import pl.otekplay.plugin.config.ConfigRecipe
import java.util.*

class ConfigGenerator(
        val name:String,
        val recipe: ConfigRecipe,
        val buildBlockId: Int,
        val buildBlockDestroyTime: Long
) {

    fun isItemGenerator(item: ItemStack) = recipe.result.toItemStack().isSimilar(item)

    fun buildGenerator(uniqueId:UUID,location: Location) = Generator(uniqueId,location, Material.getMaterial(buildBlockId), buildBlockDestroyTime, System.currentTimeMillis()+buildBlockDestroyTime)

}
