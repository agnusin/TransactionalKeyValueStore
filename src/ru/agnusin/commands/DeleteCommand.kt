package ru.agnusin.commands

import ru.agnusin.store.Store

class DeleteCommand : Command<Unit> {
    override val label: String
        get() = "DELETE"

    override fun getAction(vararg args: String): Action<Unit> {
        checkArguments(args.size == 1)

        return object : Action<Unit> {
            val key = args[0]

            override fun invoke(store: Store): Action.Result<Unit> {
                return store.remove(key)
                    ?.let { Action.Result.Success(Unit) }
                    ?: Action.Result.Error("key not set")
            }
        }
    }
}