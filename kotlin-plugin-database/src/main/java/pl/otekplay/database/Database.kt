package pl.otekplay.database

import com.mongodb.async.client.MongoClients
import com.mongodb.async.client.MongoCollection
import com.mongodb.client.result.DeleteResult
import org.bson.Document

class Database(val plugin: DatabasePlugin) {
    private val connection = MongoClients.create(plugin.config.url)
    private val database = connection.getDatabase(plugin.config.database)
    private val resultInsert = DatabaseResult<Void>(plugin)
    private val resultUpdate = DatabaseResult<Document>(plugin)
    private val resultDelete = DatabaseResult<DeleteResult>(plugin)

    fun getCollection(table: String): MongoCollection<Document> = database.getCollection(table)

    fun insertEntity(entity: DatabaseEntity) = getCollection(entity.collection).insertOne(serializeEntity(entity), resultInsert)

    fun updateEntity(entity: DatabaseEntity) = getCollection(entity.collection).findOneAndReplace(generateKey(entity), serializeEntity(entity), resultUpdate)

    fun deleteEntity(entity: DatabaseEntity) = getCollection(entity.collection).deleteOne(generateKey(entity), resultDelete)

    private fun serializeEntity(entity: DatabaseEntity) = Document.parse(plugin.databaseSeralizer.toJson(entity)) !!

    private fun generateKey(entity: DatabaseEntity) = Document(entity.key, entity.id)

    inline fun <reified T> loadObject(collection: String, id: String, crossinline listener: (data: T?) -> Unit) {
        getCollection(collection)
                .find(Document("id", id))
                .first { document, throwable ->
                    if (throwable == null)
                        listener.invoke(plugin.databaseSeralizer.fromJson(plugin.databaseSeralizer.toJson(document), T::class.java))
                    else
                        listener.invoke(null)
                }
    }

    inline fun <reified T> loadObjects(collection: String, id: String, crossinline listener: (data: ArrayList<T>) -> Unit) {
        val list = arrayListOf<T>()
        getCollection(collection)
                .find(Document("id", id))
                .forEach({
                    list.add(plugin.databaseSeralizer.fromJson(plugin.databaseSeralizer.toJson(it), T::class.java))
                }, { _, err ->
                    if (err != null) {
                        plugin.logger.info("Can't load collection from $collection, error: ${err.message}")
                        err.printStackTrace()
                    } else {
                        listener.invoke(list)
                    }
                })
    }


    inline fun <reified T> loadCollection(collection: String, crossinline listener: (data: ArrayList<T>) -> Unit) {
        val list = arrayListOf<T>()
        getCollection(collection)
                .find()
                .forEach({
                    list.add(plugin.databaseSeralizer.fromJson(plugin.databaseSeralizer.toJson(it), T::class.java))
                }, { _, err ->
                    if (err != null) {
                        plugin.logger.info("Can't load collection from $collection, error: ${err.message}")
                        err.printStackTrace()
                    } else {
                        listener.invoke(list)
                    }
                })
    }

    fun removeCollection(name: String) = getCollection(name).drop({ _, _ -> })

}

