package pl.otekplay.plugin.api

import java.util.logging.Logger

class

PluginLogger(anno: PluginAnnotation, parent: Logger) : Logger(anno.name, null) {
    private val prefix = "[${anno.name}]"

    init {
        setParent(parent)
    }

}


