package pl.otekplay.database.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.UUID

class LocationTypeAdapter : TypeAdapter<Location>() {
    override fun write(jsonWriter: JsonWriter, location: Location?) {
        if (location == null) {
            jsonWriter.nullValue()
            return
        }

        jsonWriter.beginObject()

        jsonWriter.name("x").value(location.x)
        jsonWriter.name("y").value(location.y)
        jsonWriter.name("z").value(location.z)
        jsonWriter.name("yaw").value(location.yaw)
        jsonWriter.name("pitch").value(location.pitch)
        jsonWriter.name("world").value(location.world.uid.toString())

        jsonWriter.endObject()
    }

    override fun read(jsonReader: JsonReader): Location? {
        if (jsonReader.peek() === JsonToken.NULL) {
            jsonReader.nextNull()
            return null
        }

        val location = Location(Bukkit.getWorlds()[0], 0.0, 0.0, 0.0)

        jsonReader.beginObject()

        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                "x" -> location.x = jsonReader.nextDouble()
                "y" -> location.y = jsonReader.nextDouble()
                "z" -> location.z = jsonReader.nextDouble()
                "yaw" -> location.yaw = java.lang.Double.valueOf(jsonReader.nextDouble()).toFloat()
                "pitch" -> location.pitch = java.lang.Double.valueOf(jsonReader.nextDouble()).toFloat()
                "world" -> location.world = Bukkit.getWorld(UUID.fromString(jsonReader.nextString()))
            }
        }

        jsonReader.endObject()

        return location
    }
}