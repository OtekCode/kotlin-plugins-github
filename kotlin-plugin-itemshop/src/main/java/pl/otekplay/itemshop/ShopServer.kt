package pl.otekplay.itemshop

import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.launch
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

class ShopServer(val plugin: ItemShopPlugin,private val addr: InetSocketAddress) {
    private val server = ServerSocket()
    private val clients = hashSetOf<Socket>()
    val logger get() = plugin.logger
    private val handler = launch(start = CoroutineStart.LAZY) {
        while (isActive) {
            logger.info("Server is waiting on next client")
            val nextSocket = server.accept()
            clients.add(nextSocket)
            logger.info("Server added new client [IP: ${nextSocket.inetAddress}]")
            val client = factory.createClient(nextSocket)
            clientManager.registerClient(client)
        }
    }
    private val factory = ShopClientFactory(this)
    internal val clientManager = ShopClientManager(this)


    fun getClients() = clientManager.clients


    val closed get() = server.isClosed

    fun open() {
        logger.info("Binding server on [IP: ${addr.address}/${addr.port}]")
        server.bind(addr)
        handler.start()
    }

    fun stop() {
        logger.info("Closing server sockets...")
        server.close()
        handler.cancel()
    }
}
