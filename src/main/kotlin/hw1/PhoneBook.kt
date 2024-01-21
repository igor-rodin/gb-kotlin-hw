package hw1

const val CMD_EXIT = "exit"
const val CMD_HELP = "help"
const val CMD_ADD = "add"
const val SUB_CMD_PHONE = "phone"
const val SUB_CMD_EMAIL = "email"

const val ERR_WRONG_CMD = 0
const val ERR_PARAMS_COUNT = 1
const val ERR_WRONG_PHONE = 2
const val ERR_WRONG_EMAIL = 3

fun main() {
    var inputData = getUserInput()
    var command = inputData[0].lowercase()
    while (command != CMD_EXIT) {
        when (command) {
            CMD_HELP, "?" -> showHelp()
            CMD_ADD -> processAddCommand(inputData.subList(1, inputData.size))
            else -> showError(ERR_WRONG_CMD, command)
        }
        inputData = getUserInput()
        command = inputData[0].lowercase()
    }
    println("Выходим...")
}

/**
 * Process add command with [params]
 */
private fun processAddCommand(params: List<String>) {
    /**
     * Process subCommand ([SUB_CMD_PHONE], [SUB_CMD_EMAIL]) for add command
     * @return validated argument of subcommand or null
     */
    fun processSubcommand(subCommand: String): String? {
        when (subCommand) {
            SUB_CMD_PHONE -> {
                val phone = params.last()
                if (!validatePhone(phone)) {
                    showError(ERR_WRONG_PHONE, phone)
                    return null
                }
                return phone
            }

            SUB_CMD_EMAIL -> {
                val email = params.last()
                if (!validateEmail(email)) {
                    showError(ERR_WRONG_EMAIL, email)
                    return null
                }
                return email
            }

            else -> {
                showError(ERR_WRONG_CMD, subCommand)
                return null
            }
        }
    }

    if (params.size != 3) {
        showError(ERR_PARAMS_COUNT, CMD_ADD)
        return
    }
    val subCommand = params[1].lowercase()
    val result = processSubcommand(subCommand) ?: return
    println("INFO: $CMD_ADD ${params.first()} $subCommand $result")
}

/**
 * Get user input from console
 * @return split input as list of String
 */
fun getUserInput(): List<String> {
    print("Команда (? - для справки): > ")
    return readlnOrNull()?.split(Regex("""\s+""")) ?: emptyList()
}

/**
 * Print error message
 * @param errType error type ([ERR_WRONG_CMD], [ERR_PARAMS_COUNT], [ERR_WRONG_PHONE], [ERR_WRONG_EMAIL])
 * @param params optional params
 */
private fun showError(errType: Int, vararg params: String) {
    when (errType) {
        ERR_WRONG_CMD -> println("Неизвестная команда ${params[0]}")
        ERR_PARAMS_COUNT -> {
            println("Неверное количество параметров для команды ${params[0]}")
            println(
                """
                |Usage: ADD <Name> PHONE <Phone>
                |       ADD <Name> EMAIL <Email>
            """.trimMargin()
            )
        }

        ERR_WRONG_PHONE -> {
            println("Неверный формат телефонного номера: ${params[0]}")
        }

        ERR_WRONG_EMAIL -> {
            println("Неверный email: ${params[0]}")
        }
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
            EXIT - выход из приложения
    """.trimIndent()
    println(help)
}

/**
 * Validate [phone] number
 * @return true if valid or false
 */
private fun validatePhone(phone: String): Boolean {
    val phoneRegex = Regex("""^(\+)?\d+$""")
    return phoneRegex.matches(phone)
}

/**
 * Validate [email] number
 * @return true if valid or false
 */
fun validateEmail(email: String): Boolean {
    val emailRegex = Regex("""^[\w-]+@([\w-]+\.)[\w-]{2,4}$""")
    return emailRegex.matches(email)
}
