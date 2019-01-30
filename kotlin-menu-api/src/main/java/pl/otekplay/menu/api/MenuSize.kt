package pl.otekplay.menu.api

enum class MenuSize(val size: Int) {
    ONE_LINE(9),
    TWO_LINE(18),
    THREE_LINE(27),
    FOUR_LINE(36),
    FIVE_LINE(45),
    SIX_LINE(54);

    companion object {
        fun fit(slots: Int): MenuSize {
            return when {
                slots < 10 -> ONE_LINE
                slots < 19 -> TWO_LINE
                slots < 28 -> THREE_LINE
                slots < 37 -> FOUR_LINE
                slots < 46 -> FIVE_LINE
                else -> SIX_LINE
            }
        }
    }
}

