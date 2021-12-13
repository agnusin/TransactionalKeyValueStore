package ru.agnusin.commands

import ru.agnusin.store.Store

class GetCommand : Command<String> {
    override val label: String
        get() = "GET"

    override fun getAction(vararg args: String): Action<String> {
        checkArguments(args.size == 1)

        return object : Action<String> {
            val key = args[0]

            override fun invoke(store: Store): Action.Result<String> {
                return store.get(key)
                    ?.let { v ->
                        Action.Result.Success(v)
                    } ?: Action.Result.Error("key not set")
            }
        }
    }
}