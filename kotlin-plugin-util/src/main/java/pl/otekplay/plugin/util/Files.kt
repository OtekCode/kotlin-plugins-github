package pl.otekplay.plugin.util

import org.bukkit.ChatColor
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object Files {

    fun saveJson(file:File,string: String){
        if(file.exists()) file.delete()
        Files.write(file.toPath(),string.toByteArray(Charsets.UTF_8))
    }





}