package ru.agnusin.commands

import ru.agnusin.store.Store

class SetCommand: Command<Unit> {
    override val label: String
        get() = "SET"

    override fun getAction(vararg args: String): Action<Unit> {
        checkArguments(args.size == 2)

        return object : Action<Unit> {
            val key = args[0]
            val value = args[1]

            override fun invoke(store: Store): Action.Result<Unit> {
                store.put(key, value)
                return Action.Result.Success(Unit)
            }
        }
    }
}