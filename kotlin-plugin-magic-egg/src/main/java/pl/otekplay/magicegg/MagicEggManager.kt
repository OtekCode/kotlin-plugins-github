package pl.otekplay.magicegg

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets

class MagicEggManager(private val plugin: MagicEggPlugin) {
    val eggs = arrayListOf<MagicEgg>()

    fun getMagicEgg(name:String) = eggs.singleOrNull { it.name == name }

    fun getMagicEggByItem(item:ItemStack) = eggs.singleOrNull { it.result.toItemStack().isSimilar(item) }


    init {
        saveExample()
        loadEggs()
    }
    private fun saveExample(){
        val example = MagicEgg("example",2.0,
                ConfigItem("MagiczneJajko", Material.DRAGON_EGG.id),
                arrayListOf(
                        ConfigEnchantedItem("Helm",Material.DIAMOND_HELMET.id,enchants = hashMapOf(
                                Pair(Enchantment.DURABILITY.id,3)
                        ))
                ))
        Files.saveJson(File(plugin.folder, example.name), plugin.pluginLoader.gson.toJson(example))

    }

    fun loadEggs(){
        eggs.clear()
        plugin.folder
                .listFiles()
                .filter { ! it.name.contains("Config") }
                .forEach {
                    val egg = plugin.pluginLoader.gson.fromJson(  it.readText(), MagicEgg::class.java)
                    if (! egg.name.contains("example")) eggs.add(egg)
                }
        plugin.logger.info("Loaded " + eggs.size + " boxes.")

    }
}