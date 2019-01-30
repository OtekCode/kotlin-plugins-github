package pl.otekplay.market

import pl.otekplay.market.creator.CreatorConfig
import pl.otekplay.market.creator.CreatorManager
import pl.otekplay.market.offer.OfferConfig
import pl.otekplay.market.owner.OwnerConfig
import pl.otekplay.market.offer.OfferManager
import pl.otekplay.market.offer.OfferMenu
import pl.otekplay.market.owner.OwnerListener
import pl.otekplay.market.owner.OwnerManager
import pl.otekplay.market.owner.OwnerMenu
import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("MarketPlugin", [])
class MarketPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: MarketConfig
    lateinit var ownerConfig: OwnerConfig
    lateinit var offerConfig: OfferConfig
    lateinit var creatorConfig: CreatorConfig
    lateinit var ownerManager: OwnerManager
    lateinit var ownerMenu: OwnerMenu
    lateinit var marketManager: MarketManager
    lateinit var marketMenu: MarketMenu
    lateinit var offerManager: OfferManager
    lateinit var offerMenu: OfferMenu
    lateinit var creatorManager: CreatorManager

    override fun onEnable() {
        loadConfig()
        ownerManager = OwnerManager(this)
        ownerMenu = OwnerMenu(this)
        marketManager = MarketManager(this)
        marketMenu = MarketMenu(this)
        offerManager = OfferManager(this)
        offerMenu = OfferMenu(this)
        creatorManager = CreatorManager(this)
        registerListener(OwnerListener(this))
        registerCommand(MarketCommand(this))
    }


    fun loadConfig() {
        config = loadConfig(MarketConfig::class)
        ownerConfig = loadConfig(OwnerConfig::class)
        offerConfig = loadConfig(OfferConfig::class)
    }
}