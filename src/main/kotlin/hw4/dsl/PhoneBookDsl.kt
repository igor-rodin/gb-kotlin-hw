package hw4.dsl

import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

@PBJsonMarker
class PersonJson {
    var name: String? = null
    var phones: Set<String> = HashSet()
    var emails: Set<String> = HashSet()

    override fun toString() =
        """{"name": "$name", "phones": ${
            phones.joinToString(
                ", ",
                prefix = "[",
                postfix = "]",
                transform = { "\"$it\"" })
        }, "emails": ${
            emails.joinToString(
                ", ",
                prefix = "[",
                postfix = "]",
                transform = { "\"$it\"" })
        }}"""
}

@PBJsonMarker
class PhoneBookJson {
    private var persons: MutableList<PersonJson> = mutableListOf()

    fun contact(init: PersonJson.() -> Unit): PersonJson {
        val person = PersonJson().apply(init)
        persons.add(person)
        return person
    }

    override fun toString() = persons.joinToString(", ", prefix = "[", postfix = "]", transform = PersonJson::toString)
}

fun createPhoneBookJson(init: PhoneBookJson.() -> Unit): PhoneBookJson = PhoneBookJson().apply(init)
