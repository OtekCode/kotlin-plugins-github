package pl.otekplay.backup

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions

class BackupConfig : PluginConfiguration {

    val messages = BackupMessages()
    val backupAutoSaveTime = 10 * 60 * 30L

    val optionsMenuBackupList = ConfigMenuOptions("Lista Backupow", 6, ConfigMenuFill(false, ConfigItem("Clean", Material.AIR.id)))
    val optionsMenuBackup = ConfigMenuOptions("Backup %id%", 1, ConfigMenuFill(false, ConfigItem("Clean", Material.AIR.id)))
    val backupMenuItem = ConfigItem("Backup", Material.WOOD_SWORD.id, 1, 0, arrayListOf(
            "Id: %id%",
            "Data: %date%",
            "Type: %type%",
            "Itemy: %contents%",
            "Armor: %armor%",
            "Enderchest: %enderchest%",
            "Kliknij aby zobaczyc rozwinac opcje!"
    ))

    val menuOptionsItemsShowInventory = ConfigMenuOptions("Inventory", 6, ConfigMenuFill(false, ConfigItem("Clean", Material.AIR.id)))
    val menuOptionsItemsShowArmor = ConfigMenuOptions("Armor", 1, ConfigMenuFill(false, ConfigItem("Clean", Material.AIR.id)))
    val menuOptionsItemsShowEnderchest = ConfigMenuOptions("Enderchest", 6, ConfigMenuFill(false, ConfigItem("Clean", Material.AIR.id)))

    val menuItemContents = ConfigMenuItem(1, ConfigItem("Itemy", Material.CHEST.id, 1, 0, listOf(
            "Kliknij aby zobaczyc liste itemow z eq!"
    )))

    val menuItemArmor = ConfigMenuItem(2, ConfigItem("Armor", Material.DIAMOND_CHESTPLATE.id, 1, 0, listOf(
            "Kliknij aby zobaczyc zbroje!"
    )))

    val menuItemEnderchest = ConfigMenuItem(3, ConfigItem("Enderchest", Material.ENDER_CHEST.id, 1, 0, listOf(
            "Kliknij aby zobaczyc liste itemow z enderchesta!"
    )))

    val menuItemUse = ConfigMenuItem(5, ConfigItem("Uzyj!", Material.WOOD_BUTTON.id, 1, 0, listOf(
            "Kliknij aby uzyc tego backupa na graczu!"
    )))

    val menuItemBackFromOptions = ConfigMenuItem(8, ConfigItem("Wroc!", Material.ARROW.id, 1, 0, listOf(
            "Kliknij aby wrocic do listy backupow!"
    )))

    val menuItemBackFromItems = ConfigItem("Wroc!", Material.ARROW.id, 1, 0, listOf(
            "Kliknij aby wrocic do opcji backupow!"
    ))
}