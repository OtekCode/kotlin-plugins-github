package pl.otekplay.permissions

class PermissionMessages{
    val groupNotFound = "Nie znaleziono grupy o takiej nazwie"
    val groupListHeader = "Lista grup"
    val groupListFormat = "> %group%, uprawnien: %permissions%"
    val groupInfoFormat = listOf(
            "Grupa %group%",
            "Nazwa grupy: %group%",
            "Ilosc uprawnien: %permissions%"
    )
    val youChangedPlayerGroup = "Zmieniles grupe gracza %name% na %group%"
    val userInfoFormat = listOf(
            "Nick: %name%",
            "Grupa: %group%",
            "User: %superuser%")
    val superuserEnabled = "Superuser"
    val superuserDisabled = "Normal"
    val youReloadedConfig = "Przeladowano uprawnienia!"
    val youReloadedUsers = "Przeladowano graczy!"
    val youEnabledSuperuser = "Ustawiono gracza %name% jako superusera!"
    val youDisabledSuperuser = "Ustawiono gracza %name% jako superusera!"
    val listSuperuserHeader = "Lista superuserow"
    val listSuperuserFormat = "%name%"
    val youAddedPermToUser = "Dodales permisje %perm% graczowi %name%"
    val youRemovePermFromUser = "Usuneles permisje %perm% graczowi %name%"
    val playerDontHaveThisPerm = "Gracz %name% nie posiada permisji %perm%"
    val playerAlreadyHaveThisPerm = "Gracz %name% posiada juz %perm%"
}