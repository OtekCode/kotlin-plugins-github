package pl.otekplay.mysterybox

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets

class MysteryBoxManager(private val plugin: MysteryBoxPlugin) {
    val boxes = arrayListOf<MysteryBox>()

    init {
        saveDefaultBox()
        loadBoxes()
    }

    private fun saveDefaultBox() {
        val example = MysteryBox("example", ConfigItem("SuperBox!", Material.CHEST.id, 1), arrayListOf(
                ConfigEnchantedItem("SuperKilof", Material.DIAMOND_PICKAXE.id, 1, 0, arrayListOf("super", "kilof", "dla", "kiry"),
                        hashMapOf(
                                Pair(Enchantment.DIG_SPEED.id, 6),
                                Pair(Enchantment.DURABILITY.id, 3),
                                Pair(Enchantment.LOOT_BONUS_BLOCKS.id, 3))
                ),
                ConfigEnchantedItem("Bejkonheheszky", Material.BEACON.id, 1)
        ), "Gracz %name% otworzyl super boxa i otrzymal %boxName% ilosci %amount%")
        Files.saveJson(File(plugin.folder, example.name), plugin.pluginLoader.gson.toJson(example))
    }

    fun loadBoxes() {
        boxes.clear()
        plugin.folder
                .listFiles()
                .filter { !it.name.contains("Config") }
                .forEach {
                    val json = plugin.pluginLoader.gson.fromJson(it.readText(Charsets.UTF_8), MysteryBox::class.java)
                    if (!json.name.contains("example")) boxes.add(json)
                }
        plugin.logger.info("Loaded " + boxes.size + " boxes.")
    }
}