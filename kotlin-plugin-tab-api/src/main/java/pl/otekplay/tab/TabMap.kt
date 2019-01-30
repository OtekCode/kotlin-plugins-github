package pl.otekplay.tab

import pl.otekplay.plugin.util.Randoms

class TabMap{

    fun generateMap(): Array<String> {
        val list = arrayListOf<String>()
        for (i in 1..80) generateString(list)
        return list.toTypedArray()
    }

    private fun generateString(list: ArrayList<String>) {
        val string = Randoms.getRandomStringWithoutNumbers(6).toLowerCase()
        if (list.contains(string)) generateString(list) else list.add(string)
    }
}