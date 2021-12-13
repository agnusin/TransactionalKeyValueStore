package ru.agnusin.commands

import ru.agnusin.store.Store

class CountCommand: Command<Int> {
    override val label: String
        get() = "COUNT"

    override fun getAction(vararg args: String): Action<Int> {
        checkArguments(args.size == 1)

        return object : Action<Int> {
            val value = args[0]

            override fun invoke(store: Store): Action.Result<Int> {
                val count = store.values().count { it == value }
                return Action.Result.Success(count)
            }
        }
    }
}