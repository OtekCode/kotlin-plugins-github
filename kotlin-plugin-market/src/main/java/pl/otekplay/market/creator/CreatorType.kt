package pl.otekplay.market.creator

import org.bukkit.Material
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

enum class CreatorType(
        val config: CreatorConfig
) {
    BUILD(CreatorConfig(ConfigMenuOptions("Budowanie", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Budowanie", Material.STONE.id), CreatorType.ITEMS_BUILD)),
    WEAPON(CreatorConfig(ConfigMenuOptions("Bronie", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Bronie", Material.DIAMOND_SWORD.id), CreatorType.ITEMS_WEAPON)),
    ARMOR(CreatorConfig(ConfigMenuOptions("Zbroja", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Zbroja", Material.DIAMOND_CHESTPLATE.id), CreatorType.ITEMS_ARMOR)),
    TOOL(CreatorConfig(ConfigMenuOptions("Narzedzia", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Narzedzia", Material.DIAMOND_PICKAXE.id), CreatorType.ITEMS_TOOL)),
    DECORATION(CreatorConfig(ConfigMenuOptions("Dekoracje", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Dekoracje", Material.YELLOW_FLOWER.id), CreatorType.ITEMS_DECORATION)),
    FOOD(CreatorConfig(ConfigMenuOptions("Jedzenie", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Jedzenie", Material.COOKED_CHICKEN.id), CreatorType.ITEMS_FOOD)),
    MATERIALS(CreatorConfig(ConfigMenuOptions("Materialy", 6, ConfigMenuFill.DEFAULT), ConfigEnchantedItem("Materialy", Material.DIAMOND.id), CreatorType.ITEMS_MATERIALS));

    companion object {
        private val ITEMS_BUILD = arrayListOf(
                CreatorItem(BUILD, 0, Material.STONE, "Kamien"),
                CreatorItem(BUILD, 1, Material.DIRT, "Ziemia")
        )
        private val ITEMS_WEAPON = arrayListOf(
                CreatorItem(BUILD, 0, Material.DIAMOND_SWORD, "Diamentowy Miecz"),
                CreatorItem(BUILD, 1, Material.BOW, "Luk")
        )
        private val ITEMS_ARMOR = arrayListOf(
                CreatorItem(BUILD, 0, Material.LEATHER_HELMET),
                CreatorItem(BUILD, 1, Material.LEATHER_CHESTPLATE),
                CreatorItem(BUILD, 2, Material.LEATHER_LEGGINGS),
                CreatorItem(BUILD, 3, Material.LEATHER_BOOTS),
                CreatorItem(BUILD, 9, Material.IRON_HELMET),
                CreatorItem(BUILD, 10, Material.IRON_CHESTPLATE),
                CreatorItem(BUILD, 11, Material.IRON_LEGGINGS),
                CreatorItem(BUILD, 12, Material.IRON_BOOTS),
                CreatorItem(BUILD, 19, Material.GOLD_HELMET),
                CreatorItem(BUILD, 20, Material.GOLD_CHESTPLATE),
                CreatorItem(BUILD, 21, Material.GOLD_LEGGINGS),
                CreatorItem(BUILD, 22, Material.GOLD_BOOTS),
                CreatorItem(BUILD, 31, Material.DIAMOND_HELMET),
                CreatorItem(BUILD, 32, Material.DIAMOND_CHESTPLATE),
                CreatorItem(BUILD, 33, Material.DIAMOND_LEGGINGS),
                CreatorItem(BUILD, 34, Material.DIAMOND_BOOTS)
        )
        private val ITEMS_TOOL = arrayListOf(
                CreatorItem(BUILD, 0, Material.STONE, "Kamien"),
                CreatorItem(BUILD, 1, Material.DIRT, "Ziemia")
        )
        private val ITEMS_DECORATION = arrayListOf(
                CreatorItem(BUILD, 0, Material.STONE, "Kamien"),
                CreatorItem(BUILD, 1, Material.DIRT, "Ziemia")
        )
        private val ITEMS_FOOD = arrayListOf(
                CreatorItem(BUILD, 0, Material.STONE, "Kamien"),
                CreatorItem(BUILD, 1, Material.DIRT, "Ziemia")
        )
        private val ITEMS_MATERIALS = arrayListOf(
                CreatorItem(BUILD, 0, Material.STONE, "Kamien"),
                CreatorItem(BUILD, 1, Material.DIRT, "Ziemia")
        )

    }

}
