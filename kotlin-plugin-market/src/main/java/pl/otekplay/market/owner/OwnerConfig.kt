package pl.otekplay.market.owner

import pl.otekplay.plugin.api.PluginConfiguration
import pl.otekplay.plugin.config.ConfigMenuFill
import pl.otekplay.plugin.config.ConfigMenuOptions

class OwnerConfig: PluginConfiguration {
    val optionsMenuItemAdd = ConfigMenuOptions("Dodaj przedmioty do listy marketu!",5, ConfigMenuFill.DEFAULT)
    val optionsMenuItemRemove = ConfigMenuOptions("Twoje itemy", 5, ConfigMenuFill.DEFAULT)
    val optionsMenuItemManage = ConfigMenuOptions("Stworz oferte", 5, ConfigMenuFill.DEFAULT)
    val messages = OwnerMessages()

}
//
//val itemNeedAdd = ConfigMenuItem(5, ConfigItem("Dodaj wymagany item!", Material.TORCH.id))
//val removeNeedItem = ConfigMenuItem(5, ConfigItem("Usun wymagany item!", Material.REDSTONE_TORCH_OFF.id))