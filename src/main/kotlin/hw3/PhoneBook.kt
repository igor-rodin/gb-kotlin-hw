package hw3


object PhoneBook {
    private val _phoneBook: MutableMap<String, Person> = mutableMapOf()


    fun addPhoneToPerson(personName: String, phone: String) {
        if (_phoneBook[personName] == null) {
            _phoneBook[personName] = Person(personName).apply {
                addPhone(phone)
            }
        } else {
            _phoneBook[personName]?.addPhone(phone)
        }
    }

    fun addEmailToPerson(personName: String, email: String) {
        if (_phoneBook[personName] == null) {
            _phoneBook[personName] = Person(personName).apply {
                addEmail(email)
            }
        } else {
            _phoneBook[personName]?.addEmail(email)
        }

    }

    fun findPersonByName(personName: String): Person? = _phoneBook[personName]

    fun findPersonsByPhoneOrEmail(query: String): List<Person> =
        _phoneBook.values.filter { it.emails.contains(query) || it.phones.contains(query) }


}