package ru.agnusin

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.core.Store
import ru.agnusin.store.HashMapStore
import ru.agnusin.store.commands.CountCommand
import ru.agnusin.store.commands.DeleteCommand
import ru.agnusin.store.commands.GetCommand
import ru.agnusin.store.commands.SetCommand
import ru.agnusin.transactions.TransactionManager

fun main() {
    val keyValueStore = KeyValueStore(getDefaultCommandSet(), HashMapStore())
    println("Welcome to Transactional Key Value Store")
    while (true) {
        readLine()?.let { str ->
            val result = keyValueStore.interpret(str)
            if (result.isNotEmpty()) println(result)
        }
    }
}

private fun getDefaultCommandSet(): CommandSet {
    return setOf(
        SetCommand(),
        GetCommand(),
        DeleteCommand(),
        CountCommand()
    )
}

class KeyValueStore(
    private val commandSet: CommandSet,
    store: Store
) {

    private val transactionManager = TransactionManager(store)


    fun interpret(str: String): String {
        val expression = parse(str) ?: return ""

        val command = translateExpressionToCommand(expression) ?: return "not supported command"
        return try {
            val action = command.getAction(*expression.args)
            when (val result = transactionManager.execAction(action)) {
                is Action.Result.Success -> {
                    if (result.data is Unit) ""
                    else result.data.toString()
                }
                is Action.Result.Error -> result.msg
            }
        } catch (e: IllegalArgumentException) {
            e.message!!
        }
    }

    private fun parse(str: String): Expression? {
        val commandAndArguments = str.split(" ", limit = 2)
        if (commandAndArguments.isEmpty()) return null

        return if (commandAndArguments.size > 1) {
            val args = commandAndArguments[1].split(" ").filter { it.isNotBlank() }.toTypedArray()
            Expression(commandAndArguments[0], args)
        } else Expression(commandAndArguments[0], emptyArray())
    }

    private fun translateExpressionToCommand(exp: Expression): Command<*, *>? {
        for (command in transactionManager.supportedCommandSet) {
            if (command.label == exp.commandLabel) {
                return command
            }
        }

        for (command in commandSet) {
            if (command.label == exp.commandLabel) {
                return command
            }
        }

        return null
    }

    data class Expression(
        val commandLabel: String,
        val args: Array<String>
    )
}

typealias CommandSet = Set<Command<Store, *>>