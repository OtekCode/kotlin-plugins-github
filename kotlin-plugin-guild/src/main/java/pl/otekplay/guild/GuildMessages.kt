package pl.otekplay.guild

import java.util.concurrent.TimeUnit

class GuildMessages {
    val guildIsProtected = "&7Gildia &6%tag% jest pod ochrona!"
    val youCantJoinGuildNeedItems = "&7Musisz posiadac &6%amount% kamienia aby dolaczyc do gildii!"
    val guildDestroyedByOther = "&7Gracz &6%name% z gildii &6%attacker% &7zniszczyl zycie gildii &6%destroyed%"
    val guildHasBeenDestroyed = "&7Gildia &6%tag% &7zostala zniszczona!"
    val yourGuildDestroyedOtherYouGotLife = "&7Twoja gildia otrzymala zycie za zniszczenie gildii &6%tag%"
    val yourGuildDestroyedOtherMaxLifes = "&7Twoja gildia podbila gildie &6%tag% &7ale macie juz maksymalna ilosc zyc!"
    val yourGuildHasBeenDestroyedLostLife = "&7Twoja gildia zostala pobita przez gildie &6%tag% &7straciliscie zycie!"
    val guildHasBeenExpired = "&7Gildia &6%tag% &7wygasla, jej kordynaty x: &6%x% &7z: &6%z%"
    val guildTimeToExpire = "&7Twoja gildia wygasnie za: &6%time% &7godzin"
    val cantBreakHereGuild = "&7Nie mozesz niszczyc na terenie czyjes gildi."
    val cantPlaceHereGuild = "&7Nie mozesz budowac na terenie czyjes gildi."
    val pvPInGuildIsDisabled = "&7&4PvP w gildi jest wylaczone!"
    val pvPBowInGuildIsDisabled = "&7&4PvP w gildi za pomoca luku jest wylaczone!"
    val youAlreadyGotGuild = "&7Posiadasz juz gildie!"
    val guildNameIsReserved = "&7Nazwa &6%name% &7jest juz zajeta!"
    val guildTagIsReserved = "&7Tag &6%tag% &7jest juz zajety"
    val guildTagIsInvalid = "&7Tag musi zawierac 4 znaki."
    val guildIsTooCloseSpawn = "&7Jestes za blisko spawnu.."
    val guildIsTooCloseGuild = "&7Jestes za blisko innej gildi."
    val guildHasBeenCreated = "&7Gracz &6%name% stworzyl gildie &6%tag% %name%"
    val youDontHaveGuild = "&7Nie posiadasz gildii."
    val youAreNotLeader = "&7Nie jestes liderem gildii."
    val playerHasAlreadyGuild = "&7Gracz &6%name% &7ma juz gildie."
    val yourGuildCantHaveMoreMembers = "&7Masz za duzo czlonkow w gildii, nie mozesz dodac kolejnego."
    val youCantJoinGuildIsFull = "&7Nie mozesz dolaczyc do gilldi, ma ona za duzo czlonkow."
    val youInvitedPlayerToGuild = "&7Zaprosiles gracza &6%name% &7do gildii."
    val youGotInvitedToGuild = "&7Gracz &6%name% &7zaprosil Cie do gildi &6%tag%"
    val youCantLeaveGuildWhileLeader = "&7Nie mozesz opuscic gildi dopoki jestes liderem."
    val playerLeaveFromGuild = "&7Gracz &6%name% &7opuscil gildie &6%tag%"
    val playerIsNotInYourGuild = "&7Gracz &6%name% &7nie jest w twojej gildi."
    val cantKickPlayerIsLeader = "&7Nie mozesz wyrzucic gracza &6%name% &7jest liderem twojej gildii"
    val playerGotKickFromGuild = "&7Gracz &6%name% &7zostal usuniety z gildii &6%tag%"
    val youGotKickedFromGuild = "&7Zostales wyrzucony z gildii przez &6%remover%"
    val cantFindGuildWithTag = "&7Gildia z tagiem &6%tag% &7nie istnieje."
    val youDontHaveInviteFromGuild = "&7Nie posiadasz zaproszenia do gildii &6%tag%"
    val playerJoinedToGuild = "&7Gracz &6%name% &7dolaczyl do gildii &6%tag%"
    val guildHasBeenDeleted = "&7Gildia &6%tag% &7zostala zamknieta."
    val guildLeaderHasBeenChanged = "&7Gracz &6%name% &7zostal nowym liderem gildii&6 %tag%  "
    val guildListCommandHeader = "&7Lista Gildia"
    val guildListCommandFormat = "&7Tag: &6%tag% &7Leader: &6%leader% &7Name: &6%name%"
    val guildHomeDelay = TimeUnit.SECONDS.toMillis(10)
    val guildCantSetHomeOnOtherGuild = "&7Nie mozesz ustawiac home poza terenem gildii."
    val guildHomeHasBeenChanged = "&7Home gildii zostalo zmienione!"
    val guildDontHaveAllyWithYou = "&7Gildia &6%tag% &7nie ma sojuszu z wami!"
    val guildPeelAllyWithGuild = "&7Gildia &6%tag% &7zerwala sojusz z gildia &6%war%"
    val yourGuildHasAlreadyAlly = "&7Twoja gildia jest juz w sojuszu z gildia &6%tag%"
    val guildSendInviteToAlly = "&7Gildia &6%tag% &7wyslala zaproszenie do sojuszu!"
    val yourGuildSendInviteAlly = "&7Twoja gildia wyslala zaproszenie do sojuszu gildi &6%tag%"
    val guildAlliedWithGuild = "&7Gildia &6%tag% zawarla sojusz z gildia %ally%"
    val yourGuildAlreadySendInvite = "&7Twoja gildia juz wyslala zaproszenie do sojuszu gildii &6%tag% "
    val youCantAllyYourGuild = "&7Nie mozesz wyslac sojuszu do swojej gildii!"
    val guildBowEnabled = "&7PvP za pomoca luku w gildi zostalo wlaczone."
    val guildBowDisabled = "&7PvP za pomoca luku w gildi zostalo wylaczone."
    val guildPvPEnabled = "&7PvP w gildi zostalo wlaczone."
    val guildPvPDisabled = "&7PvP w gildi zostalo wylaczone."
    val youDontHaveGuildItems = "&7Nie posiadasz wystarczajaca ilosc itemow, wpisz &6/g itemy &7aby sprawdzic liste itemow!"
    val guildCreateIsNotAvailable = "&7Tworzenie gildii bedzie dostepne dopiero &6%date%"
    val allItemsMustBeDiscovered = "&7Wszystkie itemy na gildie musza byc odkryte jesli chcesz zalozyc gildie."
    val youMovedOnEnemyGuildTerrain = "&7Wszedles na teren wrogiej gildii &6%tag%"
    val youMovedOnAllyGuildTerrain = "&7Wszedles na teren sojuszniczej gildii &6%tag%"
    val youMovedOnYourGuildTerrain = "&7Wszedles na teren swojej gildii &6%tag%"
    val youMovedOnWildressTerrain = "&7Opusciles teren gildii &6%tag%"
    val guildBuildHasBeenBlockedByTnT = "&7Na terenie gildii wybuchlo TnT, od teraz budowanie bedzie niedostepne przez jakis czas."
    val guildBuildIsBlockedByExplode = "&7Na terenie gildii niedawno wybuchlo tnt, musicie chwile odczekac"
    val yourGuildBuildIsBlockedTo = "&7Twoja gildia bedzie mogla budowac dopiero o: &6%date%"
    val yourGuildBuildIsNotBlocked = "&7Twoja gildia moze normalnie budowac."
    val youTeleportedToGuild = "&7Przeteleportowales sie do gildii &6%tag%"
    val youReloadedGuildConfig = "&7Przeladowales configi gildii!"
    val guildInfoMessage = arrayListOf(
            "&7Tag: &6%tag%",
            "&7Name: &6%name%",
            "&7Leader: &6%leader%",
            "&7Members: &6%members%",
            "&7Allies: &6%allies%",
            "&7Cuboid: &6%cuboid%",
            "&7Create: &6%create%",
            "&7Lives: &6%lives%",
            "&7Protection: &6%protection%",
            "&7Expire: &6expire%",
            "&7Punkty: &6%points%",
            "&7Kills: &6%kills%",
            "&7Deaths: &6%deaths%",
            "&7Assists: &6%assists%",
            "&7Place: &6%place%"
    )
    val enemyIsOnTerrainGuild = "&7Intruz jest na terytorium twojej gildii!"
}