package hw3


object MainCmd {
    const val EXIT = "exit"
    const val HELP = "help"
    const val HELP_SHORT = "?"
    const val ADD = "add"
    const val SHOW = "show"
}
object SubCmd {
    const val PHONE = "phone"
    const val EMAIL = "email"
}

sealed interface Command {
    fun isValid(): Boolean = true

    data object Help : Command
    data object Exit : Command
    data object Show : Command
    data class Add(val name: String, val data: PersonData) : Command {
        override fun isValid() = name.isNotEmpty() && data.isValid()
    }

    data class Wrong(val message: String) : Command
}
