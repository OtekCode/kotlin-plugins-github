package pl.otekplay.generator

import org.bukkit.scheduler.BukkitRunnable

class GeneratorTask(private val plugin: GeneratorPlugin) : BukkitRunnable() {
    override fun run() {
        val generators = plugin.manager.getNeedRepairGenerators()
        generators.forEach { it.repairGenerator() }
        plugin.manager.repairGenerators(generators.map { it.uniqueId })
    }
}