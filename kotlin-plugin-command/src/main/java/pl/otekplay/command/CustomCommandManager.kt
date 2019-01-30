package pl.otekplay.command

import pl.otekplay.plugin.util.Files
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.charset.StandardCharsets

class CustomCommandManager(private val plugin: CustomCommandPlugin) {
    private val _commands = HashMap<String, CustomCommand>()
    private val folder = File(plugin.folder.parentFile.parentFile, "commands")
    val commands get() = _commands.values

    init {
        if (!folder.exists()) folder.mkdir()
        saveExampleCommands()
        loadCommands()
    }

    fun getCommand(string: String) = _commands[string]

    private fun saveExampleCommands() {
        val vip = CustomCommand(
                "vip", listOf(),
                listOf(
                        "&7Czas: &6Cala Edycja!",
                        "&7Cena: &611.07zl",
                        "&7Platnosc: &6https://gohc.pl/shop/vip/def",
                        "&7Co posiada &6VIP",
                        "&7- Brak delaya na czat pomiedzy wiadomosciami.",
                        "&7- Unikalny prefix na czat!",
                        "&7- Wiekszy drop z kamienia na serwerze!",
                        "&7- O wiele wiekszy bonus z TurboDrop!",
                        "&7- Mozliwosc ustawienia 2 home!",
                        "&7- Unikalny prefix na czat!",
                        "&7Zestaw itemow:",
                        "&7Set(Diamentowy): [&64/3&7], Miecz: [&65/2&7]&7, Kilof: [&65/3/3&7]",
                        "&7Koxy[&6x3&7], Refy[&6x6&7], Perly[&6x4&7]"
                )
        )
        val svip = CustomCommand(
                "svip", listOf(),
                listOf(
                        "&7Czas: &6Cala Edycja!",
                        "&7Cena: &617.22zl",
                        "&7Platnosc: &6https://gohc.pl/shop/svip/def",
                        "&7Co posiada &6SVIP",
                        "&7- Brak delaya na czat pomiedzy wiadomosciami.",
                        "&7- Unikalny prefix na czat!",
                        "&7- Jeszcze bardziej wiekszy bonus z TurboDrop!",
                        "&7- Wiekszy drop z kamienia na serwerze!",
                        "&7- Mozliwosc ustawienia 3 home!",
                        "&7- Unikalny prefix na czat!",
                        "&7Zestaw itemow:",
                        "&7Set(Diamentowy): [&64/3&7], Miecz: [&65/2&7]&7, Miecze: [&6x2&7] [&65/3/3&7]",
                        "&7Koxy[&6x10&7], Refy[&6x32&7], Perly[&6x8&7], Kilof: [&65/3/3&7]"
                )
        )
        val help = CustomCommand(
                "pomoc",
                listOf("help", "hel", "?", "h", "pom"),
                listOf(
                        "&7Lista przydatnych komend na serwerze:",
                        "&7-&6 /helpop <wiadomosc> &7 - &6Umozliwia kontakt z administracja.",
                        "&7-&6 /gracz <nick> &7 - &6Pokazuje podstawowe informacje o danym graczu.",
                        "&7-&6 /g info <tag> &7 - &6Pokazuje podstawowe informacji o danej gildii.",
                        "&7-&6 /g pomoc &7 - &6Pokazuje liste komend od gildii.",
                        "&7-&6 /vip &7 - &6Informajce o VIP",
                        "&7-&6 /svip &7 - &6Informacja o SVIP",
                        "&7-&6 /craftingi &7 - &6Otwiera menu z craftingami.",
                        "&7-&6 /kity &7 - &6Otwiera menu z kitami.",
                        "&7-&6 /drop &7 - &6Otwiera menu z dropami."
                )
        )
        val helpGuilds = CustomCommand(
                "g pomoc",
                listOf("gildie", "factions", "guilds", "guild", "gpomoc", "ghelp"),
                listOf(
                        "&7Lista komend gildii na serwerze:",
                        "&7-&6 /g info <tag> &7 - &6Pokazuje podstawowe informacji o danej gildii",
                        "&7-&6 /g zaloz <tag> <nazwa> &7 - &6Zaklada gildie z wybranym tagiem i nazwa",
                        "&7-&6 /g dolacz <tag> &7 - &6Dolacza do danej gildii jesli masz zaproszenie",
                        "&7-&6 /g opusc &7 - &6Opuszcza gildie jesli w niej jestes [BEZ POTWIERDZENIA, UWAZAJ]",
                        "&7Komendy dostepne tylko dla lidera:",
                        "&7-&6 /g zapros <nick> &7 - &6Zaprasza wybranego gracza do gilldi",
                        "&7-&6 /g wyrzuc <nick> &7 - &6Wyrzuca gracza z gildii",
                        "&7-&6 /g lider <nick> &7 - &6Oddaje lidera wybranej osobie",
                        "&7-&6 /g sojusz <tag> &7 - &6Wysyla zaproszenie sojusznice do danej gildii",
                        "&7-&6 /g wojna <tag> &7 - &6Zrywa sojusz z podana gildia",
                        "&7-&6 /g ulepszenia &7 - &6Wyswietla menu z ulepszeniami w gildii.",
                        "&7-&6 /g usun &7 - &6Usuwa twoja gildie[BEZ POTWIERDZENIA, UWAZAJ]"
                )
        )
        arrayOf(vip, svip, help, helpGuilds).forEach { saveCommand(it) }


    }


    private fun saveCommand(customCommand: CustomCommand) {
        Files.saveJson(File(folder, customCommand.name), plugin.gson.toJson(customCommand))
    }

    fun loadCommands() {
        _commands.clear()
        folder.listFiles().filter { !it.name.contains("example", true) }.forEach {
            plugin.logger.info("Loading ${it.name} custom command.")
            val command = plugin.gson.fromJson(it.readText(Charsets.UTF_8), CustomCommand::class.java)
            if (command == null) {
                plugin.logger.info("Can't load ${it.name} command file.")
                return
            }
            _commands[command.name] = command
            command.aliases.forEach {
                _commands[it] = command
            }
            plugin.logger.info("CustomCommand ${it.name} has been loaded.")
        }
        plugin.logger.info("Loaded ${_commands.size} custom _commands.")
    }
}