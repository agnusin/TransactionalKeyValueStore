package ru.agnusin.store.actions

import ru.agnusin.store.core.Store

class SetValueAction(args: List<String>): ValueAction<Unit>(args) {

    override fun exec(t: Store): Result<Unit> {
        return checkArgumentsAndRun(args.size == 2) {
            val entry = Store.Entry(args[0], args[1])
            t.put(entry)

            Result.Success(Unit)
        }
    }
}