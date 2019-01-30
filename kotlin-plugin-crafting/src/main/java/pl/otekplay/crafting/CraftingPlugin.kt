package pl.otekplay.crafting

import org.bukkit.Material
import org.bukkit.entity.Player
import pl.otekplay.menu.api.Menu
import pl.otekplay.menu.api.MenuConfig
import pl.otekplay.menu.api.items.Item
import pl.otekplay.menu.api.items.ItemClick
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions
import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.logging.Logger

@PluginAnnotation(name = "CraftingPlugin", dependency = [])
class CraftingPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: CraftingConfig
    private val craftings = arrayListOf<ConfigCraft>()


    override fun onEnable() {
        config = loadConfig(CraftingConfig::class)
        registerCommand(CraftingCommand(this))
        loadCraftings()

    }


    fun reloadConfig() {
        config = loadConfig(CraftingConfig::class)
        loadCraftings()
    }


    private fun saveCraftingExample(file: File) {
        val example = ConfigCraft(
                ConfigMenuOptions("Title", 5, ConfigMenuFill(true, MenuConfig.fillerItem)),
                ConfigMenuItem(1, ConfigItem("Option", Material.DIAMOND.id)),
                arrayListOf(
                        ConfigMenuItem(10, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(11, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(12, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(19, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(20, ConfigItem("", Material.DIAMOND.id)),
                        ConfigMenuItem(21, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(28, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(29, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(30, ConfigItem("", Material.STONE.id)),
                        ConfigMenuItem(23, ConfigItem("Generator!", Material.SPONGE.id))
                )
        )
        Files.saveJson(File(file, "craftingexample"), gson.toJson(example))
    }

    private fun loadCraftings() {
        craftings.clear()
        logger.info("Loading crafting items...")
        val craftingFolder = File(folder, "craftings")
        if (!craftingFolder.exists()) craftingFolder.mkdir()
        saveCraftingExample(craftingFolder)
        craftingFolder
                .listFiles()
                .filter { !it.name.contains("example") }
                .forEach {
                    val crafting = gson.fromJson(it.readText(Charsets.UTF_8), ConfigCraft::class.java)
                    craftings.add(crafting)
                }
        logger.info("Loaded crafting items: ${craftings.size}")
    }

    fun showCraftingMenu(player: Player) {
        val menu = Menu(config.menuCrafting)
        craftings.forEach {
            val optionItem = it.optionItem
            menu.setItem(optionItem.slot, Item(optionItem.item.toItemStack(), getOpenAction(it)))
        }
        menu.open(player)
    }

    private fun getOpenAction(cfg: ConfigCraft) = object : ItemClick {
        override fun click(player: Player) {
            showCraftMenu(player, cfg)
        }
    }

    private fun showCraftMenu(player: Player, configCraft: ConfigCraft) {
        val menu = Menu(configCraft.menuOptions)
        configCraft.infoItems.forEach {
            menu.setItem(it.slot, Item(it.item.toItemStack()))
        }
        menu.open(player)
    }
}