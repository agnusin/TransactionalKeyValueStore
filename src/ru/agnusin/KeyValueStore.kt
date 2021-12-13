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
        val commandAndArguments = str.split(" ", limit = 2)
        if (commandAndArguments.isEmpty()) return ""

        for (com in commandSet) {
            if (com.label == commandAndArguments[0]) {
                return try {
                    val arguments = commandAndArguments.takeIf { it.size == 2 }?.get(1) ?: ""
                    val action = com.getAction(arguments)
                    handleAction(action)
                } catch (e: IllegalArgumentException) {
                    e.message!!
                }
            }
        }

        return "not supported command"
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
}

typealias CommandSet = Set<Command<*>>