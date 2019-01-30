package pl.otekplay.plugin.config

import org.bukkit.Material
import org.bukkit.inventory.ShapedRecipe

data class ConfigRecipe(
        val result: ConfigItem,
        private val shape: ConfigRecipeShape,
        private val materials: Map<Char, Int>
){

    fun toShapedRecipe(): ShapedRecipe {
        val shaped = ShapedRecipe(result.toItemStack())
        shaped.shape(shape.first,shape.second,shape.third)
        materials.entries.forEach {
            shaped.setIngredient(it.key,Material.getMaterial(it.value))
        }
        return shaped
    }
}

