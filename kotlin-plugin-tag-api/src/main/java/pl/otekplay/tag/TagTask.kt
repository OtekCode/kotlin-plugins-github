package pl.otekplay.tag

import org.bukkit.scheduler.BukkitRunnable

class TagTask(val plugin: TagPlugin): BukkitRunnable() {
    override fun run() {
        TagAPI.refresh()
    }

}