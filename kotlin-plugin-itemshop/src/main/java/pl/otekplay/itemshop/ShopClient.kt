package pl.otekplay.itemshop

import kotlinx.coroutines.experimental.launch
import java.net.Socket

class ShopClient(private val server: ShopServer, val id: Int, val socket: Socket) {
    private val input = socket.getInputStream()
    private val output = socket.getOutputStream()
    var len = -1
    private val reader = launch {
        while (socket.isConnected) {
            try {
                len = if (len == -1) input.read() else len
                if (len > input.available()) continue
                val byteArray = ByteArray(len)
                input.read(byteArray)
                len = -1
                server.logger.info("Socket client read packet[ID: $id] (Bytes: ${byteArray.size}, Available: ${input.available()})")
                server.clientManager.handle(this@ShopClient, String(byteArray))
            } catch (e: Exception) {
                close()
                server.logger.info("Socket client id$id find a exception, closing connection.")
                break
            }
        }
        close()
    }

    init {
        reader.start()
    }

    private fun close() {
        reader.cancel()
        socket.close()
        server.clientManager.unregisterClient(this)
    }


    fun writeString(string: String) {
        val len = string.length
        val bytes = string.toByteArray()
        server.logger.info("Socket client write packet[ID: $id] (Len: $len, Bytes: ${bytes.size}, String: $string)")
        output.write(string.length)
        output.write(string.toByteArray())
        output.flush()
    }

}