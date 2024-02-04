package hw4

data class Person(val name: String) {
    private val _phones: MutableSet<String> = mutableSetOf()
    private val _emails: MutableSet<String> = mutableSetOf()

    val phones: Set<String>
        get() = _phones

    val emails: Set<String>
        get() = _emails

    fun addPhone(phone: String) {
        _phones.add(phone)
    }

    fun addEmail(email: String) {
        _emails.add(email)
    }

    override fun toString() = "-------------------\nКонтакт $name:\n" +
            "Телефоны: ${_phones.joinToString(", ")}\n" +
            "Emails: ${_emails.joinToString(", ")}" +
            "\n-------------------"
}

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

