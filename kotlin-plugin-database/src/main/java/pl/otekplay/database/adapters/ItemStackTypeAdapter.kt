package pl.otekplay.database.adapters

/*
class ItemStackTypeAdapter : TypeAdapter<ItemStack>() {
    override fun write(jsonWriter: JsonWriter, itemStack: ItemStack?) {
        if (itemStack == null) {
            jsonWriter.nullValue()
            return
        }

        if (item == null) {
            jsonWriter.nullValue()
            return
        }

        jsonWriter.beginObject()
        jsonWriter.name("type")

        jsonWriter.value(itemStack.type.toString())
        jsonWriter.name("amount")

        jsonWriter.value(itemStack.amount)
        jsonWriter.name("data")


        jsonWriter.value(itemStack.durability)
        jsonWriter.name("tag")

        if (item!!.getTag() != null) {
            jsonWriter.value(nbtToString(itemStack.))
        } else jsonWriter.value("")

        jsonWriter.endObject()
    }

    override fun read(jsonReader: JsonReader): ItemStack? {
        if (jsonReader.peek() == JsonToken.NULL) {
            return null
        }

        jsonReader.beginObject()

        jsonReader.nextName()
        val type = Material.getMaterial(jsonReader.nextString())

        jsonReader.nextName()
        val amount = jsonReader.nextInt()

        jsonReader.nextName()
        val data = jsonReader.nextInt()

        val item = net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(type), amount, data)

        jsonReader.nextName()
        val next = jsonReader.nextString()

        if (next.startsWith("{")) {
            val compound = MojangsonParser.parse(ChatColor.translateAlternateColorCodes('&', next))

            item.setTag(compound)
        }

        jsonReader.endObject()

        return CraftItemStack.asBukkitCopy(item)
    }
}*/