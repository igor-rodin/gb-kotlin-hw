package hw2

enum class CmdType {
    ;

    enum class Main(val title: String) {
        EXIT("exit"), HELP("help"), HELP_SHORT("?"), ADD("add"), SHOW("show");
    };

    enum class Sub(val title: String) {
        PHONE("phone"), EMAIL("email")
    }

    companion object {
        //val titles = CmdType.entries.associateBy{it.title}
        fun mainFromTitle(title: String): Main? = Main.entries.find { it.title == title }
        fun subFromTitle(title: String): Sub? = Sub.entries.find { it.title == title }
    }
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
