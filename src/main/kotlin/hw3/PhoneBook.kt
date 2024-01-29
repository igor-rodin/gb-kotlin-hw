package hw3


class PhoneBook {
    private val _phoneBook: MutableMap<String, Person> = mutableMapOf()


    fun addPhoneToPerson(personName: String, phone: String) {
        _phoneBook.getOrPut(personName) { Person(personName) }.addPhone(phone)
    }

    fun addEmailToPerson(personName: String, email: String) {
        _phoneBook.getOrPut(personName) { Person(personName) }.addEmail(email)
    }

    fun findPersonByName(personName: String): Person? = _phoneBook[personName]

    fun findPersonsByPhoneOrEmail(query: String): List<Person> =
        _phoneBook.values.filter { it.emails.contains(query) || it.phones.contains(query) }


}