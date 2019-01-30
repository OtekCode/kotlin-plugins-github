package pl.otekplay.drop

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions

class DropConfig : PluginConfiguration {

    val messages = DropMessages()

    val breakBlockedIds = arrayListOf(
            16,
            15,
            14,
            21,
            56,
            73,
            129,
            153)
    val menuOptions = ConfigMenuOptions("Menu", 4, ConfigMenuFill(false, ConfigItem("Fill", 4)))
    val dropLore = arrayListOf(
            "Nazwa: %name%",
            "Ilosc od: %min% Do: %max%",
            "Szansa: %chance%",
            "Permission: %perm%",
            "PlayerExp: %playerexp%",
            "DropExp: %dropexp%",
            "Biome: %biomes%",
            "Zebrane: %collected%",
            "Wlaczony: %enabled%"
    )
    val menuDropDisabledString = "Nie"
    val menuDropEnabledString = "Tak"

    val menuItemOptions = ConfigMenuOptions("Menu Drop", 1, ConfigMenuFill(false, ConfigItem("Fill", 4)))

    val menuBonusItem = ConfigMenuItem(0, ConfigItem("Dostepne bonusy:", Material.DIAMOND_PICKAXE.id, 1, 0))
    val menuBonusItemLore = arrayListOf(
            "------- >",
            "Bonusy:",
            "Fortuna: %fortune%",
            "Permisje: %permbonus%",
            "Turbo: %turbo%",
            "Wymagane:",
            "Wysokosc: %height%",
            "Kilof: %pickaxe%",
            "Prawo: %perm%",
            "Godzina: %hours%",
            "Wystartowal: %startdate%",
            "------- >"
    )


    val menuHourItem = ConfigMenuItem(1, ConfigItem("Dostepne godziny:", Material.WATCH.id, 1, 0))
    val menuHourItemLore = arrayListOf(
            "Teraz: %now%",
            "Od: %from%",
            "Do: %to%"
    )


    val menuPickAxeItemName = "Wymagany kilof:"
    val menuPickAxeItemSlot = 2
    val menuPickAxeItemLore = arrayListOf(
            "Posiadasz: %pickaxe%",
            "Od: %min%",
            "Do: %max%"
    )

    val menuHeightItem = ConfigMenuItem(3, ConfigItem("Wymagana wysokosc:", Material.LEATHER_BOOTS.id, 1, 0))
    val menuHeightItemLore = arrayListOf(
            "Posiadasz: %height%",
            "Od: %min%",
            "Do: %max%"
    )

    val menuFortuneItem = ConfigMenuItem(4, ConfigItem("Fortuna", Material.EMERALD.id, 1, 0))
    val menuFortuneItemLore = arrayListOf(
            "Bonus od Fortuny:",
            "Aktywny: %enabled%",
            "Szansa: %chance% ",
            "PlayerExp: %playerexp% ",
            "DropExp: %dropexp% ",
            "BonusAmount: %amount% "
    )

    val menuPermissionItem = ConfigMenuItem(5, ConfigItem("Permisja", Material.NAME_TAG.id, 1, 0))
    val menuPermissionItemLore = arrayListOf(
            "Bonus od Fortuny:",
            "Aktywny: %enabled%",
            "Permisja: %perm%",
            "Szansa: %chance% ",
            "PlayerExp: %playerexp% ",
            "DropExp: %dropexp% ",
            "BonusAmount: %amount% "
    )

    val menuTurboItem = ConfigMenuItem(6, ConfigItem("Turbo", Material.DIAMOND_PICKAXE.id, 1, 0))
    val menuTurboItemLore = arrayListOf(
            "Bonus od Turbo:",
            "Aktywny: %enabled%",
            "Do: %date%",
            "Szansa: %chance% ",
            "PlayerExp: %playerexp% ",
            "DropExp: %dropexp% ",
            "BonusAmount: %amount% "
    )
    val menuOptionItem = ConfigMenuItem(7, ConfigItem("Opcje", Material.BOOK.id, 1, 0))
    val menuOptionItemLore = arrayListOf(
            "Wlaczony: %enabled%",
            "Zebrane: %collected%"
    )

    val menuExitItem = ConfigMenuItem(8, ConfigItem("Wroc do Menu", Material.BOOKSHELF.id, 1, 0))
    val menuExitItemLore = arrayListOf("Kliknij aby wrocic do menu z dropami!")
    val levelUpgrades = hashMapOf(
            Pair(2,500),
            Pair(3,1000),
            Pair(4,1500),
            Pair(5,2000),
            Pair(6,2500),
            Pair(7,3000),
            Pair(8,3500),
            Pair(9,4000),
            Pair(10,5000)
    )

}