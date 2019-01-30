package pl.otekplay.fight

class FightMessages {
    val antyRelog = "&4[ANTYRELOG]"
    val playerLoggedWhileAntyRelog = "&7Gracz &6%name% &7wylogowal sie podczas walki!"
    val listPlayersWhileFighting = "&7Gracze ktorzy walcza: &6%players%"
    val youReloadedConfig = "&7Przeladowales config!"
    val commandsWhileCombatAreDisabled = "&7Komendy podczas pvp sa wylaczone!"
    val allowedCommmandsWhileCombat = listOf(
            "drop",
            "msg"
    )
}