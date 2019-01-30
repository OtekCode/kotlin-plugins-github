package pl.otekplay.generator

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigRecipe
import pl.otekplay.plugin.config.ConfigRecipeShape
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

class GeneratorManager(private val plugin: GeneratorPlugin) {
    private val folder = File(plugin.folder, "generators")
    val configGenerators = arrayListOf<ConfigGenerator>()
    private val generators = HashMap<UUID, Generator>()
    private val generatorsByLocations = HashMap<Location, UUID>()
    private val destroyed = arrayListOf<UUID>()


    init {
        loadGenerators()
        DatabaseAPI.loadAll<Generator>("generators", {
            it.forEach {
                generators[it.uniqueId] = it
                generatorsByLocations[it.location] = it.uniqueId
                destroyed.add(it.uniqueId)
            }
            plugin.logger.info("Loaded ${generators.size} generators.")
        })

    }

    fun getConfigWithItem(itemStack: ItemStack) = configGenerators.singleOrNull { it.isItemGenerator(itemStack) }

    private fun getGenerator(uniqueId: UUID) = generators[uniqueId]

    private fun getGeneratorIdByLocation(location: Location) = generatorsByLocations[location]

    fun getGeneratorByLocation(location: Location): Generator? {
        return getGenerator(getGeneratorIdByLocation(location) ?: return null)
    }

    fun getNeedRepairGenerators() = destroyed
            .mapNotNull { generators[it] }
            .filter { it.needRepair() }
            .filter { it.canRepair() }

    fun repairGenerators(collection: Collection<UUID>) = destroyed.removeAll(collection)

    fun destroyGenerator(location: Location) {
        val uniqueId = getGeneratorIdByLocation(location) ?: return
        val generator = getGenerator(uniqueId) ?: return
        generator.repairTime = generator.buildTime + System.currentTimeMillis()
        destroyed.add(generator.uniqueId)
    }

    fun createGenerator(location: Location, config: ConfigGenerator) {
        val uniqueId = UUID.randomUUID()
        plugin.logger.info("Trying to create generator with ID: $uniqueId")
        val generator = config.buildGenerator(uniqueId, location)
        generator.insertEntity()
        generators[uniqueId] = generator
        generatorsByLocations[location] = uniqueId
        destroyed.add(uniqueId)
        plugin.logger.info("Generator with ID: $uniqueId has been created.")
    }

    fun getConfigFromGenerator(generator: Generator) = configGenerators.singleOrNull { it.buildBlockDestroyTime == generator.buildTime && it.buildBlockId == generator.buildMaterial.id }

    fun removeGenerator(uniqueId: UUID) {
        plugin.logger.info("Removing generator with ID: $uniqueId")
        val generator = getGenerator(uniqueId) ?: return
        generator.deleteEntity()
        generator.location.block.type = Material.AIR
        generators.remove(uniqueId)
        plugin.logger.info("Generator with ID: $uniqueId has been removed.")
    }

    private fun saveExample() {
        val configGenerator = ConfigGenerator(
                "SuperGenerator",
                ConfigRecipe(
                        ConfigItem("Generator", Material.SPONGE.id),
                        ConfigRecipeShape("aaa", "aba", "aaa"),
                        mapOf(
                                Pair('a', Material.STONE.id),
                                Pair('b', Material.DIAMOND.id)
                        )
                ),
                Material.STONE.id,
                1000
        )
        Files.saveJson(File(folder, "examplegenerator"), plugin.gson.toJson(configGenerator))
    }

    fun loadGenerators() {
        configGenerators.clear()
        if (!folder.exists()) folder.mkdir()
        saveExample()
        plugin.logger.info("Loading config generators...")
        folder.listFiles().filter { !it.name.contains("example") }.forEach {
            plugin.logger.info("Loading ${it.name} generator file...")
            val generator = plugin.gson.fromJson(it.readText(), ConfigGenerator::class.java)
            configGenerators.add(generator)
            Bukkit.addRecipe(generator.recipe.toShapedRecipe())
        }
        plugin.logger.info("Loaded ${configGenerators.size} config generators")

    }


}
