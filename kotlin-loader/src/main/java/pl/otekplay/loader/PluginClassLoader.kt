package pl.otekplay.loader

import java.net.URL
import java.net.URLClassLoader

internal class PluginClassLoader(private val classLoader: KotlinClassLoader, url: URL, parentLoader: ClassLoader) : URLClassLoader(Array(1, { url }), parentLoader) {
    override fun loadClass(name: String?, resolve: Boolean): Class<*> {
        return loadClass0(name, resolve, true)
    }

    internal fun loadClass0(name: String?, resolve: Boolean, others: Boolean): Class<*> {
        return try {
            super.loadClass(name, resolve)
        } catch (e: ClassNotFoundException) {
            if (!others) {
                throw e
            }
            this.classLoader.getClass(name, resolve, this)
        }
    }

    fun unload() {
        this.classLoader.loaders.remove(this)
        this.close()
    }
}