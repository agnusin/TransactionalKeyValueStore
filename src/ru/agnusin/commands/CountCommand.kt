package ru.agnusin.commands

import ru.agnusin.store.Store
import java.security.InvalidParameterException

class CountCommand: Command<Int> {
    override val label: String
        get() = "COUNT"

    override fun getAction(str: String): Action<Int> {
        val value = splitArguments(str)
            .takeIf { it.size == 1 }
            ?.first()
            ?: throw InvalidParameterException("wrong number of arguments")

        return object : Action<Int> {

            override fun invoke(store: Store): Action.Result<Int> {
                val count = store.values().count { it == value }
                return Action.Result.Success(count)
            }
        }
    }
}