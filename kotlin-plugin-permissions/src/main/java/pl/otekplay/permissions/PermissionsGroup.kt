package pl.otekplay.permissions

data class PermissionsGroup(
        val name: String,
        val default: Boolean,
        var permissions: ArrayList<String> = arrayListOf()
)