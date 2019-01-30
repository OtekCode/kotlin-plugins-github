package pl.otekplay.tools

abstract class ToolsManager(val plugin: ToolsPlugin) {
    val config = plugin.config


    fun init() {
        plugin.logger.info("Initalizing manager ${this.javaClass.simpleName}")
        plugin.logger.info("Registering commands...")
        registerCommands()
        plugin.logger.info("Registering listeners...")
        registerListeners()
        plugin.logger.info("Registering variables...")
        registerVariables()
    }

    abstract fun registerVariables()

    abstract fun registerCommands()

    abstract fun registerListeners()
}