package pl.otekplay.bans

class BanMessages {
    val playerHasAlreadyBanIP = "&7IP &6%ip% &7posiada juz bana."
    val playerHasAlreadyBanID = "&7Gracz &6%name% &7jest juz zbanowany."
    val playerGotPermban = "&7Gracz &6%name% &7otrzymal pernametnego bana od &6%source% &7za &6%reason%"
    val playerGotBan= "&7Gracz &6%name% &7otrzymal bana do &6%expire% &7od &6%source% &7za &6%reason%"
    val youTempBanPlayer = "&7Zbanowales gracza &6%name% &7tymczasowo"
    val youPermBanPlayer = "&7Zbanowales gracza &6%name% &7pernametnie "
    val ipIsNotBanned = "&7IP &6%ip% &7nie jest zbanowane"
    val playerIsNotBanned ="&7Gracz &6%name% &7nie posiada bana."
    val youUnbannedIp = "&7Odbanowales IP &6%ip%"
    val youUnbannedId = "&7Odbanowales gracza &6%name%"
    val youGotKicked = listOf(
            "&7[SERWER]",
            "&7Zostales wyrzucony z serwera!",
            "&7Powod: &6%reason%",
            "&7Przez: &6%source%"
    )
    val playerGotKicked = "&7Gracz &6%name% &7zostal wyrzucony z serwera przez &6%source% &7powod: &6%reason%"
}