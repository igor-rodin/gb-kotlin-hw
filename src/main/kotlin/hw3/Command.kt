package hw3


object MainCmd {
    const val EXIT = "exit"
    const val HELP = "help"
    const val HELP_SHORT = "?"
    const val ADD = "add"
    const val SHOW = "show"
    const val FIND = "find"
}
object SubCmd {
    const val PHONE = "phone"
    const val EMAIL = "email"
}

sealed interface Command {
    fun isValid(): Boolean = true

    data object Help : Command
    data object Exit : Command
    data class Show(val name: String) : Command {
        override fun isValid() = name.isNotEmpty()
    }
    data class Add(val name: String, val data: PersonData) : Command {
        override fun isValid() = name.isNotEmpty() && data.isValid()
    }

    data class Find(val query: String) : Command {
        override fun isValid() = query.isNotEmpty()
    }

    data class Wrong(val message: String) : Command
}
