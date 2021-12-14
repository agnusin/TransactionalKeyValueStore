package ru.agnusin.store.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.core.Store

class GetCommand : Command<Store, String> {
    override val label: String
        get() = "GET"

    override fun getAction(vararg args: String): ValueChangeAction<String> {
        checkArguments(args.size == 1)

        return object : ValueChangeAction<String> {
            val key = args[0]

            override fun exec(t: Store): Action.Result<String> {
                return t.get(key)
                    ?.let { v ->
                        Action.Result.Success(v)
                    } ?: Action.Result.Error("key not set")
            }
        }
    }
}