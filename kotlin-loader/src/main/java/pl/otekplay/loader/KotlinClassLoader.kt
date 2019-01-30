package pl.otekplay.loader

import java.net.URL
import java.util.*

class KotlinClassLoader(private val loader: ClassLoader) {
    internal val loaders = HashSet<PluginClassLoader>()


    internal fun getClass(name: String?, resolve: Boolean, main: PluginClassLoader): Class<*> {
        loaders.filter { it != main }.forEach {
            try {
                return it.loadClass0(name, resolve, false)
            } catch (e: ClassNotFoundException) { }
        }
        throw ClassNotFoundException("Missing dependency $name")
    }

    internal fun buildPluginLoader(url: URL) = PluginClassLoader(this, url, loader).apply { loaders.add(this) }


}