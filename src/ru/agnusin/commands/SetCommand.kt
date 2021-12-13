package ru.agnusin.commands

import ru.agnusin.store.Store
import java.security.InvalidParameterException

class SetCommand: Command<Unit> {
    override val label: String
        get() = "SET"

    override fun getAction(str: String): Action<Unit> {
        val keyValue = splitArguments(str)
            .takeIf { it.size == 2 }
            ?.let { it[0] to it[1] }
            ?: throw InvalidParameterException("wrong number of arguments")

        return object : Action<Unit> {
            override fun invoke(store: Store): Action.Result<Unit> {
                store.put(keyValue.first, keyValue.second)
                return Action.Result.Success(Unit)
            }
        }
    }
}