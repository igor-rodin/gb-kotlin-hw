package hw2

data class Person(val name: String, val phone: String?, val email: String?)
sealed interface PersonData {
    val value: String
    fun isValid(): Boolean
    data class Phone(override val value: String) : PersonData {
        override fun isValid() = Regex("""^(\+)?\d+$""").matches(value)
    }

    data class Email(override val value: String) : PersonData {
        override fun isValid() = Regex("""^[\w-]+@([\w-]+\.)[\w-]{2,4}$""").matches(value)
    }
}

