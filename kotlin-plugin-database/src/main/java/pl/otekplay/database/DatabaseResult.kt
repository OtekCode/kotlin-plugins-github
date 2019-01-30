package pl.otekplay.database

import com.mongodb.async.SingleResultCallback

class DatabaseResult<T>(val plugin: DatabasePlugin) : SingleResultCallback<T> {
    override fun onResult(result: T, throwable: Throwable?) {
        throwable?.printStackTrace()
    }


}