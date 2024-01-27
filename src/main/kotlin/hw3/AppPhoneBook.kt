package hw3


val phoneBook = PhoneBook()

fun main() {
    var inputCommand: Command
    do {
        inputCommand = readCommand()
        println(inputCommand)
        if (!inputCommand.isValid()) {
            showHelp()
            continue
        }
        when (inputCommand) {
            Command.Help, is Command.Wrong -> showHelp()
            is Command.Show -> showPersonInfo(phoneBook, inputCommand.name)
            is Command.Add -> savePersonData(phoneBook, inputCommand)
            is Command.Find -> showPersonsByPhoneOrEmail(phoneBook, inputCommand.query)
            Command.Exit -> break
        }
    } while (true)
    println("Выходим...")
}

private fun showPersonsByPhoneOrEmail(phoneBook: PhoneBook, query: String) {
    val persons = phoneBook.findPersonsByPhoneOrEmail(query)
    if (persons.isNotEmpty()) {
        println("Найдены контакты:")
        persons.forEach { println(it) }
    }
    else {
        println("Контакты не найдены")
    }
}

/**
 * Print to console information about contact with name [personMame]
 */
private fun showPersonInfo(phoneBook: PhoneBook, personMame: String) {
    val person = phoneBook.findPersonByName(personMame)

    if (person != null) println(person) else println("Контакт $personMame не найден")
}

/**
 * Save result of [inputCommand] to [phoneBook]
 */
private fun savePersonData(phoneBook: PhoneBook, inputCommand: Command.Add) {
    when (inputCommand.data) {
        is PersonData.Phone -> phoneBook.addPhoneToPerson(inputCommand.name, inputCommand.data.value)
        is PersonData.Email -> phoneBook.addEmailToPerson(inputCommand.name, inputCommand.data.value)
    }
}

/**
 * Read user input and return [Command]
 */
private fun readCommand(): Command {
    print("Команда (? - для справки): > ")
    val inputData = readlnOrNull()?.split(Regex("""\s+""")) ?: emptyList()
    if (inputData.isEmpty()) return Command.Wrong("Команда не может быть пустой")
    val cmd = inputData[0].lowercase()

    when (cmd) {
        MainCmd.HELP, MainCmd.HELP_SHORT -> return Command.Help
        MainCmd.EXIT -> return Command.Exit
        MainCmd.SHOW -> {
            if (inputData.size != 2) return Command.Wrong("Неверное количество аргументов")
            return Command.Show(inputData[1])
        }

        MainCmd.ADD -> {
            if (inputData.size != 4) return Command.Wrong("Неверное количество аргументов")
            val personData = when (inputData[2]) {
                SubCmd.PHONE -> PersonData.Phone(inputData[3])
                SubCmd.EMAIL -> PersonData.Email(inputData[3])
                else -> return Command.Wrong("Неверная команда")
            }
            return Command.Add(inputData[1], personData)
        }
        MainCmd.FIND -> {
            if (inputData.size != 2) return Command.Wrong("Неверное количество аргументов")
            return Command.Find(inputData[1])
        }
        else -> return Command.Wrong("Неверная команда")
    }
}


/**
 * Print help message
 */
private fun showHelp() {
    val help = """
        Доступные команды:
            HELP, ? - показывает это сообщение
            ADD <Name> PHONE <Phone> - добавляет телефон для <Name>
            ADD <Name> EMAIL <Phone> - добавляет email для <Name>
            SHOW <Name> - выводит информацию о контакте <Name>
            FIND <PHONE|EMAIL> - выводит контакты, содержащие <PHONE|EMAIL>
            EXIT - выход из приложения
    """.trimIndent()
    println(help)
}
