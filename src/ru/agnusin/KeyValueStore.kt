package ru.agnusin

import ru.agnusin.commands.*
import ru.agnusin.store.HashMapStore
import ru.agnusin.store.Store

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
    private val store: Store
) {

    fun interpret(str: String): String {
        val expression = parse(str) ?: return ""

        for (command in commandSet) {
            if (command.label == expression.commandLabel) {
                return try {
                    val action = command.getAction(*expression.args)
                    handleAction(action)
                } catch (e: IllegalArgumentException) {
                    e.message!!
                }
            }
        }

        return "not supported command"
    }

    private fun parse(str: String): Expression? {
        val commandAndArguments = str.split(" ", limit = 2)
        if (commandAndArguments.isEmpty()) return null

        return if (commandAndArguments.size > 1) {
            val args = commandAndArguments[1].split(" ").filter { it.isNotBlank() }.toTypedArray()
            Expression(commandAndArguments[0], args)
        } else Expression(commandAndArguments[0], emptyArray())
    }

    private fun handleAction(action: Action<*>): String {
        when (val result = action.invoke(store)) {
            is Action.Result.Success -> {
                if (result.data !is Unit) return result.data.toString()
            }
            is Action.Result.Error -> {
                return result.msg
            }
        }
        return ""
    }

    data class Expression(
        val commandLabel: String,
        val args: Array<String>
    )
}

typealias CommandSet = Set<Command<*>>