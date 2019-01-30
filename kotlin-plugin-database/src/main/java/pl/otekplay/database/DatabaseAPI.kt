package pl.otekplay.database

object DatabaseAPI {
    lateinit var plugin: DatabasePlugin
    val database get() = plugin.database

    inline fun <reified T> load(name: String, id:String, crossinline listener: (data: T?) -> Unit) = database.loadObject(name,id,listener)

    inline fun <reified T> loadById(name: String,id:String, crossinline listener: (data: ArrayList<T>) -> Unit) = database.loadObjects(name,id,listener)

    inline fun <reified T> loadAll(name: String, crossinline listener: (data: ArrayList<T>) -> Unit) = database.loadCollection(name, listener)

    fun removeCollection(name:String) = database.removeCollection(name)
}