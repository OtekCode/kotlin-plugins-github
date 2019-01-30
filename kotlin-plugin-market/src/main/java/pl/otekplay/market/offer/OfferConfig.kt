package pl.otekplay.market.offer

import org.bukkit.Material
import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuItem
import pl.otekplay.plugin.config.ConfigMenuOptions
import java.util.concurrent.TimeUnit

class OfferConfig : PluginConfiguration {
    val menuOptionsPrepareItem = ConfigMenuOptions("Tworzenie oferty!", 6, ConfigMenuFill.DEFAULT)
    val timeItems = arrayListOf(
            ConfigItemBuyTime(
                    19,
                    "6 Godzin",
                    arrayListOf(
                            "Oferta bedzie wazna przez 6 godzin.",
                            "Koszt: 6 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.HOURS.toMillis(6),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 6)
                    )
            ),
            ConfigItemBuyTime(
                    20,
                    "12 Godzin",
                    arrayListOf(
                            "Oferta bedzie wazna przez 12 godzin.",
                            "Koszt: 12 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.HOURS.toMillis(12),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 12)
                    )
            ),
            ConfigItemBuyTime(
                    21,
                    "18 Godzin",
                    arrayListOf(
                            "Oferta bedzie wazna przez 18 godzin.",
                            "Koszt: 18 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.HOURS.toMillis(18),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 18)
                    )
            ),
            ConfigItemBuyTime(
                    22,
                    "1 Dzien",
                    arrayListOf(
                            "Oferta bedzie wazna przez 1 dzien.",
                            "Koszt: 24 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.DAYS.toMillis(1),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 24)
                    )
            ),
            ConfigItemBuyTime(
                    23,
                    "2 Dni",
                    arrayListOf(
                            "Oferta bedzie wazna przez 2 dni.",
                            "Koszt: 48 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.DAYS.toMillis(2),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 48)
                    )
            ),
            ConfigItemBuyTime(
                    24,
                    "3 Dni",
                    arrayListOf(
                            "Oferta bedzie wazna przez 3 dni.",
                            "Koszt: 72 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.DAYS.toMillis(3),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 72)
                    )
            ),
            ConfigItemBuyTime(
                    25,
                    "Tydzien",
                    arrayListOf(
                            "Oferta bedzie wazna przez tydzien.",
                            "Koszt: 168 diamentow."
                    ),
                    BuyTime(
                            TimeUnit.DAYS.toMillis(3),
                            ConfigItem("Diamenty", Material.DIAMOND.id, 168)
                    )
            )
    )
    val choosedItemSlot = 0
    val choosedTimeItemBelow = ConfigItem("Wybrany!!",Material.STAINED_GLASS_PANE.id,1,13, arrayListOf("Wybrales czas powyzej!"))
    val chooseNoSelectedItem = ConfigItem(
            "Nie wybrales przedmiotu!",
            Material.BARRIER.id,
            lore = arrayListOf("Kliknij aby wybrac item!")
    )
    val chooseItemSlots = arrayListOf(1, 2, 3, 4, 5, 6, 7)
    val readyOfferItem = ConfigMenuItem(46,ConfigItem("Wystaw oferte!",Material.STONE_BUTTON.id))
    val messages=  OfferMessages()
}