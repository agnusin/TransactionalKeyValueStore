package ru.agnusin.store.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.core.Store

class SetCommand: Command<Store, Unit> {
    override val label: String
        get() = "SET"

    override fun getAction(vararg args: String): ValueChangeAction<Unit> {
        checkArguments(args.size == 2)

        return object : ValueChangeAction<Unit> {
            val key = args[0]
            val value = args[1]

            override fun exec(t: Store): Action.Result<Unit> {
                t.put(key, value)
                return Action.Result.Success(Unit)
            }
        }
    }
}