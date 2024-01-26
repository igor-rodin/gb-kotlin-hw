package hw2


var lastRecord: Person? = null

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
        MainCmd.SHOW -> return Command.Show
        MainCmd.ADD -> {
            if (inputData.size != 4) return Command.Wrong("Неверное количество аргументов")
            val personData = when (inputData[2]) {
                SubCmd.PHONE -> PersonData.Phone(inputData[3])
                SubCmd.EMAIL -> PersonData.Email(inputData[3])
                else -> return Command.Wrong("Неверная команда")
            }
            return Command.Add(inputData[1], personData)
        }

        else -> return Command.Wrong("Неверная команда")
    }
}

/**
 * Save result of [addCommand] to [lastRecord]
 * If lastRecord contains the same  contact name,
 * that addCommand contains, then lastRecord will be updated with new value of phone or email,
 * else new lastRecord will be created
 */
private fun savePerson(addCommand: Command.Add): Person? {
    val lastName: String = lastRecord?.name ?: ""
    when (addCommand.data) {
        is PersonData.Phone -> {
            if (addCommand.name == lastName) {
                return lastRecord?.copy(phone = addCommand.data.value)
            }
            return Person(addCommand.name, phone = addCommand.data.value)
        }

        is PersonData.Email -> {
            if (addCommand.name == lastName) {
                return lastRecord?.copy(email = addCommand.data.value)
            }
            return Person(addCommand.name, email = addCommand.data.value)
        }
    }
}

/**
 * Get last record
 */
private fun getLastPerson(): Person? = lastRecord

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
