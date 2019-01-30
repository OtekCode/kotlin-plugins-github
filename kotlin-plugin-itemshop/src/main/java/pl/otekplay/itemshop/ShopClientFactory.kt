package pl.otekplay.itemshop

import java.net.Socket
import java.util.concurrent.atomic.AtomicInteger

class ShopClientFactory(val server: ShopServer) {

    private val identifier = AtomicInteger(1)

    fun createClient(socket: Socket): ShopClient {
        val id = identifier.getAndIncrement()
        val client = ShopClient(server,id, socket)
        server.logger.info("Socket client has been created [ID: $id]")
        return client
    }
}