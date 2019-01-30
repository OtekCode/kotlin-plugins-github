package pl.otekplay.farmer

import org.bukkit.Bukkit
import org.bukkit.Material
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigRecipe
import pl.otekplay.plugin.config.ConfigRecipeShape
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets

class FarmerManager(private val plugin: FarmerPlugin) {
    val farmers = arrayListOf<ConfigFarmer>()

    init {
        saveDefaultFarmer()
        loadFarmers()
        registerRecipes()
    }


    private fun saveDefaultFarmer() {
        val example = ConfigFarmer("example", Material.SPONGE.id, 10, 50, true, true, false, ConfigRecipe(ConfigItem("example", Material.SPONGE.id, 1), ConfigRecipeShape("aaa", "aaa", "aaa"), hashMapOf(Pair('a', Material.WOOD.id))))
        Files.saveJson(File(plugin.folder, example.name), plugin.pluginLoader.gson.toJson(example))
    }

    fun loadFarmers() {
        farmers.clear()
        plugin.folder
                .listFiles()
                .filter { !it.name.contains("Config") }
                .forEach {
                    plugin.logger.info("Loading ${it.name} farmer file...")
                    val json = plugin.pluginLoader.gson.fromJson(it.readText(), ConfigFarmer::class.java)
                    if (!json.name.contains("example")) farmers.add(json)
                }
        plugin.logger.info("Loaded " + farmers.size + " farmers.")
    }

    fun registerRecipes() {
        farmers.forEach {
            Bukkit.addRecipe(it.recipe.toShapedRecipe())
        }
    }
}