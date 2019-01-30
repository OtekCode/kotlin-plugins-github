package pl.otekplay.teleporter

interface TeleportCallback {

    fun cancelled(teleport: Teleport) {

    }

    fun complete(teleport: Teleport)  {

    }
}