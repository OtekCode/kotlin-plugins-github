package pl.otekplay.plugin.util


object Strings {


    fun replace(source0: String, os: String, ns: String): String {
        var source = source0
        var i = source.indexOf(os, 0)

        if (i >= 0) {
            val sourceArray = source.toCharArray()
            val nsArray = ns.toCharArray()
            val oLength = os.length
            val buf = StringBuilder(sourceArray.size)
            buf.append(sourceArray, 0, i).append(nsArray)
            i += oLength
            var j = i

            while (source.indexOf(os, i) > 0) {
                i = source.indexOf(os, i)
                buf.append(sourceArray, j, i - j).append(nsArray)
                i += oLength
                j = i
            }

            buf.append(sourceArray, j, sourceArray.size - j)
            source = buf.toString()
            buf.setLength(0)
        }

        return source
    }
}