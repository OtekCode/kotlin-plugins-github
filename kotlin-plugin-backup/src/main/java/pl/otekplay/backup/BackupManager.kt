package pl.otekplay.backup

import org.bukkit.entity.Player
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigInventoryArmor
import pl.otekplay.plugin.rep
import pl.otekplay.plugin.util.Dates
import java.util.*
import kotlin.collections.HashMap

class BackupManager(private val plugin: BackupPlugin) {
    private val backups = HashMap<UUID, PlayerBackup>()

    init {
        loadBackups()
    }

    fun registerBackup(uniqueId: UUID) = backups.put(uniqueId, PlayerBackup(uniqueId, arrayListOf()).also { it.insertEntity() })

    fun getBackup(uniqueId: UUID) = backups[uniqueId]


    fun saveBackup(player: Player, type: BackupType) {
        val inventory = player.inventory
        val chest = player.enderChest
        val playerBackup = getBackup(player.uniqueId) ?: return
        val id = UUID.randomUUID().toString().substring(0, 6)
        val backup = Backup(
                id,
                System.currentTimeMillis(),
                type,
                inventory.contents.mapIndexedNotNull { i, itemStack ->

                    if(itemStack != null)
                        Pair(i,ConfigEnchantedItem(itemStack))
                    else
                        null
                }.toMap(),
                ConfigInventoryArmor(
                        ConfigEnchantedItem.buildEnchantedItemOrNull(inventory.armorContents[0]),
                        ConfigEnchantedItem.buildEnchantedItemOrNull(inventory.armorContents[1]),
                        ConfigEnchantedItem.buildEnchantedItemOrNull(inventory.armorContents[2]),
                        ConfigEnchantedItem.buildEnchantedItemOrNull(inventory.armorContents[3])
                ),
                chest.contents.mapIndexedNotNull { i, itemStack ->
                    if(itemStack != null)
                        Pair(i,ConfigEnchantedItem(itemStack))
                    else
                        null
                }.toMap()
        )
        playerBackup.backups.add(backup)
        playerBackup.updateEntity()
    }


    fun useBackup(player: Player, backup: Backup) {
        if (backup.type != BackupType.PRE_BACKUP) {
            saveBackup(player, BackupType.PRE_BACKUP)
        }
        player.sendMessage(plugin.config.messages.youGotBackup.rep("%date%", Dates.formatData(backup.time)))
        player.inventory.contents = backup.contents.values.map { it.toItemStack() }.toTypedArray()
        player.inventory.armorContents = backup.armor.toArray().map { it?.toItemStack() }.toTypedArray()
        player.enderChest.contents = backup.chest.values.map { it.toItemStack() }.toTypedArray()
    }

    fun loadBackups() {
        backups.clear()
        DatabaseAPI.loadAll<PlayerBackup>("backups", {
            it.forEach { backups[it.uniqueId] = it }
            plugin.logger.info("Loaded ${backups.size} backups.")
        })
    }
}

