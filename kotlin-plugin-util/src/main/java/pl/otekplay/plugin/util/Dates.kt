package pl.otekplay.plugin.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*




object Dates {
    private val dateFormat = DateFormat.getInstance()

    fun dateInt(): Int = Integer.valueOf(SimpleDateFormat("HHmm").format(Date(System.currentTimeMillis())))

    fun formatData(time: Long) = dateFormat.format(Date(time)).toString()

    fun parseData(string: String) = dateFormat.parse(string)

    fun isBeetwenHours(from: Int, to: Int, time:Int) = ! (to > from && time >= from && time <= to || to < from && (time >= from || time <= to))

    fun parseString(string: String): Long {
        if (string.equals("0", ignoreCase = true)) {
            return 0
        }
        val list = ArrayList<String>()
        var c: String
        var goBack = 0
        for (i in 0 until string.length) {
            c = string[i].toString()
            if (c.matches("[a-zA-Z]".toRegex())) {
                list.add(string.substring(goBack, i + 1))
                goBack = i + 1
            }
        }
        var amount: Long
        var total: Long = 0
        var ch: Char
        for (st in list) {
            ch = st[st.length - 1]
            if (st.length != 1 && ch.toString().matches("[M,w,d,h,m,s]".toRegex())) {
                amount = Math.abs(Integer.parseInt(st.substring(0, st.length - 1))).toLong()
                when (ch) {
                    's' -> total += amount * 1000
                    'm' -> total += amount * 1000 * 60
                    'h' -> total += amount * 1000 * 3600
                    'd' -> total += amount * 1000 * 3600 * 24
                    'w' -> total += amount * 1000 * 3600 * 24 * 7
                }
            }
        }

        return if (total == 0L) - 1 else total

    }

    fun parseLong(milliseconds: Long, abbreviate: Boolean): String? {
        val units = ArrayList<String>()
        var amount = milliseconds / (7 * 24 * 60 * 60 * 1000)
        units.add(amount.toString() + "w")
        amount = milliseconds / (24 * 60 * 60 * 1000) % 7
        units.add(amount.toString() + "d")
        amount = milliseconds / (60 * 60 * 1000) % 24
        units.add(amount.toString() + "h")
        amount = milliseconds / (60 * 1000) % 60
        units.add(amount.toString() + "m")
        amount = milliseconds / 1000 % 60
        units.add(amount.toString() + "s")
        val array = arrayOfNulls<String>(5)
        var end: Char
        for (s in units) {
            end = s[s.length - 1]
            when (end) {
                'w' -> {
                    array[0] = s
                    array[1] = s
                    array[2] = s
                    array[3] = s
                    array[4] = s
                }
                'd' -> {
                    array[1] = s
                    array[2] = s
                    array[3] = s
                    array[4] = s
                }
                'h' -> {
                    array[2] = s
                    array[3] = s
                    array[4] = s
                }
                'm' -> {
                    array[3] = s
                    array[4] = s
                }
                's' -> array[4] = s
            }
        }
        units.clear()
        for (s in array)
            if (! s!!.startsWith("0")) units.add(s)
        val sb = StringBuilder()
        var word: String
        var count: String
        var and: String
        var c: Char
        for (s in units) {
            if (! abbreviate) {
                c = s[s.length - 1]
                count = s.substring(0, s.length - 1)
                word = when (c) {
                    'w' -> "tygodni" + (if (count == "1") "" else "s")
                    'd' -> "dni" + (if (count == "1") "" else "s")
                    'h' -> "godzin" + (if (count == "1") "" else "s")
                    'm' -> "minut" + (if (count == "1") "" else "s")
                    else -> "sekund" + (if (count == "1") "" else "s")
                }
                and = if (s == units[units.size - 1]) "" else if (s == units[units.size - 2]) " i " else ", "
                sb.append(count).append(" ").append(word).append(and)
            } else {
                sb.append(s)
                if (s != units[units.size - 1])
                    sb.append("-")
            }
        }
        return if (sb.toString().trim { it <= ' ' }.isEmpty()) null else sb.toString().trim { it <= ' ' }

    }
}