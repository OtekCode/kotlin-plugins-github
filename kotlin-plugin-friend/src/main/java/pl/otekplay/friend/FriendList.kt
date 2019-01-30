package pl.otekplay.friend

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class FriendList(
        val uniqueId:UUID,
        val friends: HashMap<UUID,Long> = hashMapOf(),
        val invites: HashSet<UUID> = hashSetOf()
){

}