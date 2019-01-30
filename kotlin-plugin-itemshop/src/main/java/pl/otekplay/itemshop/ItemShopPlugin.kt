package pl.otekplay.itemshop

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.net.InetSocketAddress
import java.util.logging.Logger

@PluginAnnotation("ItemShopPlugin",[])
class ItemShopPlugin(
        pluginLoader: PluginLoader,
        annotation: PluginAnnotation,
        logger: Logger
) : Plugin(pluginLoader, annotation, logger) {

    lateinit var config: ItemShopConfig
    lateinit var server: ShopServer

    override fun onEnable() {
        config = loadConfig(ItemShopConfig::class)
        server = ShopServer(this, InetSocketAddress(config.hostname, config.port))
        server.open()
        registerCommand(ItemShopCommand(this))
    }

    fun reloadServer() {
        loadConfig(ItemShopConfig::class)
        if (server != null) server.stop()
        server = ShopServer(this, InetSocketAddress(config.hostname, config.port))
        server.open()
    }
}