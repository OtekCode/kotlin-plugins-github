package pl.otekplay.database

import pl.otekplay.plugin.api.PluginConfiguration

class DatabaseConfig : PluginConfiguration {
    var url = "mongodb://localhost:3306/database"
    var database = "database"
}

