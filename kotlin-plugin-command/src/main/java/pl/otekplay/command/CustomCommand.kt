package pl.otekplay.command

data class CustomCommand(
        val name: String,
        val aliases: Collection<String>,
        val messages: Collection<String>
)