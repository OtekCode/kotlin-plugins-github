package pl.otekplay.menu

import org.bukkit.Material
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.ConcurrentHashMap

class CustomMenuManager(private val plugin: CustomMenuPlugin) {
    private val menus = ConcurrentHashMap<String, CustomMenu>()

    fun getMenu(command: String) = menus[command]

    init {
        saveExampleMenu()
        loadMenus()
    }

    private fun saveExampleMenu() {
        val example = CustomMenu(
                "examplemenu",
                ConfigMenuOptions("Full", 3, ConfigMenuFill(false, ConfigItem("", Material.GLASS.id))),
                arrayListOf(
                        CustomItem(
                                "drop", ConfigMenuItem(0, ConfigItem("Skrot do dropu", Material.DIAMOND.id))
                        )
                )
        )
        Files.saveJson(File(plugin.folder, "example"), plugin.pluginLoader.gson.toJson(example))

    }

    fun loadMenus() {
        menus.clear()
        plugin.folder
                .listFiles()
                .filter { !it.name.contains("Config") }
                .forEach {
                    val json = plugin.pluginLoader.gson.fromJson(it.readText(Charsets.UTF_8), CustomMenu::class.java)
                    if (!json.command.contains("example")) menus[json.command] = json
                }
        plugin.logger.info("Loaded " + menus.size + " menus.")
    }
}