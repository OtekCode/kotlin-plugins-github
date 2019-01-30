package pl.otekplay.itemshop

import org.bukkit.Bukkit
import java.util.concurrent.ConcurrentHashMap

class ShopClientManager(val server: ShopServer) {
    private val _clients = ConcurrentHashMap<Int, ShopClient>()
    val clients get() = _clients.values

    internal fun registerClient(client: ShopClient) {
        _clients[client.id] = client
        server.logger.info("Socket client has been registered [ID: ${client.id}]")
    }

    internal fun getClient(id: Int) = _clients[id]

    internal fun unregisterClient(client: ShopClient) = _clients.remove(client.id, client)

    fun handle(client: ShopClient,string: String){
        server.plugin.runSync(Runnable {
            server.logger.info("Client id: ${client.id} invoke command: $string")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),string)
        })
    }


}