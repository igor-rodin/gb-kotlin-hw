package hw2



var lastRecord: Person? = null
fun main() {
    var inputCommand: Command?
    do {
        inputCommand = readCommand()?.also { println(it) }
        if (inputCommand == null || !inputCommand.isValid()) {
            showHelp()
            continue
        }
        when (inputCommand) {
            Command.Help -> showHelp()
            Command.Show -> showLastRecord()
            is Command.Add -> {
                lastRecord = savePerson(inputCommand)
            }

            Command.Exit -> break
        }
    } while (true)
    println("Выходим...")
}


/**
 * Read user input and return [Command] or null
 */
private fun readCommand(): Command? {
    print("Команда (? - для справки): > ")
    val inputData = readlnOrNull()?.split(Regex("""\s+""")) ?: emptyList()
    if (inputData.isEmpty()) return null
    val cmd = inputData[0].lowercase()
//    if (!CmdType.allTitles.containsKey(cmd)) return null
    when (CmdType.mainFromTitle(cmd)) {
        CmdType.Main.HELP, CmdType.Main.HELP_SHORT -> return Command.Help
        CmdType.Main.EXIT -> return Command.Exit
        CmdType.Main.SHOW -> return Command.Show
        CmdType.Main.ADD -> {
            if (inputData.size != 4) return null
            val personData: PersonData = when (CmdType.subFromTitle(inputData[2])) {
                CmdType.Sub.PHONE -> PersonData.Phone(inputData[3])
                CmdType.Sub.EMAIL -> PersonData.Email(inputData[3])
                else -> return null
            }
            return Command.Add(inputData[1], personData)
        }

        else -> return null
    }
}

/**
 * Save result of [addCommand] to [lastRecord]
 */
fun savePerson(addCommand: Command.Add): Person? {
    val lastName: String = lastRecord?.name ?: ""
    when (addCommand.data) {
        is PersonData.Phone -> {
            if (lastName.isNotEmpty() && addCommand.name == lastName) {
                return lastRecord?.copy(phone = addCommand.data.value)
            }
            return Person(addCommand.name, phone = addCommand.data.value, email = null)
        }
        is PersonData.Email -> {
            if (lastName.isNotEmpty() && addCommand.name == lastName) {
                return lastRecord?.copy(email = addCommand.data.value)
            }
            return Person(addCommand.name, phone = null, email = addCommand.data.value)
        }
    }
}

/**
 * Get last record
 */
private fun getLastPerson(): Person?  = lastRecord

/**
 * Print last record to console
 */
private fun showLastRecord() {
    println("LAST RECORD: ${getLastPerson() ?: "Not initialized"}")
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
            SHOW - выводит последнюю запись
            EXIT - выход из приложения
    """.trimIndent()
    println(help)
}
