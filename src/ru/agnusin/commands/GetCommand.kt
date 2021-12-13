package ru.agnusin.commands

import ru.agnusin.store.Store
import java.security.InvalidParameterException

class GetCommand : Command<String> {
    override val label: String
        get() = "GET"

    override fun getAction(str: String): Action<String> {
        val key = splitArguments(str)
            .takeIf { it.size == 1 }
            ?.first()
            ?: throw InvalidParameterException("wrong number of arguments")

        return object : Action<String> {
            override fun invoke(store: Store): Action.Result<String> {
                return store[key]
                    ?.let { v ->
                        Action.Result.Success(v)
                    } ?: Action.Result.Error("key not set")
            }
        }
    }
}