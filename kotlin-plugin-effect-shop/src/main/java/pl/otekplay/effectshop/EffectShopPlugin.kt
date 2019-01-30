package pl.otekplay.effectshop

import pl.otekplay.plugin.api.Plugin
import pl.otekplay.plugin.api.PluginAnnotation
import pl.otekplay.plugin.api.PluginLoader
import java.util.logging.Logger

@PluginAnnotation("EffectShopPlugin",[])
class EffectShopPlugin(pluginLoader: PluginLoader,
                       annotation: PluginAnnotation,
                       logger: Logger
):Plugin(pluginLoader, annotation, logger) {

    lateinit var config: EffectShopConfig
    lateinit var menu: EffectShopMenu

    override fun onEnable() {
        config = loadConfig(EffectShopConfig::class)
        menu = EffectShopMenu(this)
        registerCommand(EffectShopCommand(this))
    }
}