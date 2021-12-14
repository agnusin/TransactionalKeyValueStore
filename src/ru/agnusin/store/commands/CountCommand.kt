package ru.agnusin.store.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.core.Store

class CountCommand: Command<Store, Int> {
    override val label: String
        get() = "COUNT"

    override fun getAction(vararg args: String): ValueChangeAction<Int> {
        checkArguments(args.size == 1)

        return object : ValueChangeAction<Int> {
            val value = args[0]

            override fun exec(t: Store): Action.Result<Int> {
                val count = t.values().count { it == value }
                return Action.Result.Success(count)
            }
        }
    }
}