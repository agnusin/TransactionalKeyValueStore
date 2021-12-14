package ru.agnusin.store.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.core.Store

class DeleteCommand : Command<Store, Unit> {
    override val label: String
        get() = "DELETE"

    override fun getAction(vararg args: String): ValueChangeAction<Unit> {
        checkArguments(args.size == 1)

        return object : ValueChangeAction<Unit> {
            val key = args[0]

            override fun exec(t: Store): Action.Result<Unit> {
                return t.remove(key)
                    ?.let { Action.Result.Success(Unit) }
                    ?: Action.Result.Error("key not set")
            }
        }
    }
}