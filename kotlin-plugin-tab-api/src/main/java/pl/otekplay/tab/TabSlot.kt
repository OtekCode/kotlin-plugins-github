package pl.otekplay.tab

import com.comphenix.protocol.wrappers.WrappedGameProfile
import java.util.*

data class TabSlot(
        val profile: WrappedGameProfile,
        val slot: Int,
        val display:String
)