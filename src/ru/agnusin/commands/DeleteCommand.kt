package ru.agnusin.commands

import ru.agnusin.store.Store
import java.security.InvalidParameterException

class DeleteCommand : Command<Unit> {
    override val label: String
        get() = "DELETE"

    override fun getAction(str: String): Action<Unit> {
        val key = splitArguments(str)
            .takeIf { it.size == 1 }
            ?.first()
            ?: throw InvalidParameterException("wrong number of arguments")

        return object : Action<Unit> {
            override fun invoke(store: Store): Action.Result<Unit> {
                return store.remove(key)
                    ?.let { Action.Result.Success(Unit) }
                    ?: Action.Result.Error("key not set")
            }
        }
    }
}