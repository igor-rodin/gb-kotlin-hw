package hw3

data class Person(val name: String) {
    private val _phones: MutableList<String> = mutableListOf()
    private val _emails: MutableList<String> = mutableListOf()

    val phones: List<String>
        get() = _phones

    val emails: List<String>
        get() = _emails

    fun addPhone(phone: String) {
        _phones.add(phone)
    }

    fun addEmail(email: String) {
        _emails.add(email)
    }
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

